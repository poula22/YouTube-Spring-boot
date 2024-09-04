package com.example.channelmicroservice.controller;

import com.example.bussiness.model.ApiResponse;
import com.example.channelmicroservice.domain.bussiness.model.ChannelDto;
import com.example.channelmicroservice.domain.bussiness.model.ChannelUpdateDto;
import com.example.channelmicroservice.controller.utils.ChannelControllerUtils;
import com.example.channelmicroservice.domain.bussiness.ChannelService;
import com.example.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping(value = "/channel")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ChannelController {
    private final ChannelService channelService;
    private final JwtUtil jwtUtil = new JwtUtil();

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> loadChannelById(@PathVariable long id) {
        return ChannelControllerUtils
                .handleRequest(() -> channelService.loadChannelById(id));
    }
    @PostMapping
    public ResponseEntity<ApiResponse> addChannel(
            @RequestBody ChannelDto channelDto,
            @RequestHeader("Authorization") String token
    ) {
        return ChannelControllerUtils.handleRequest(() -> {
            final long ownerId = jwtUtil.extractUser(token).id();
            return channelService.addChannel(channelDto, ownerId);
        });
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> loadAllChannels() {
        return ChannelControllerUtils.handleRequest(channelService::loadAllChannels);
    }
    @GetMapping("/owner")
    public ResponseEntity<ApiResponse> loadChannelByOwnerId(@RequestHeader("Authorization") String token) {
        return ChannelControllerUtils.handleRequest(() -> {
            final long ownerId = jwtUtil.extractUser(token).id();
            return channelService.loadAllChannelsByOwnerId(ownerId);
        });
    }
    @GetMapping("/owner/{name}")
    public ResponseEntity<ApiResponse> loadChannelByOwnerName(@PathVariable String name) {
        return ChannelControllerUtils
                .handleRequest(() -> channelService.loadAllChannelsByOwnerName(name));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> removeChannelById(@PathVariable long id) {
        return ChannelControllerUtils.handleRequest(() -> {
            channelService.removeChannelById(id);
            return "Deleted";
        });
    }

    @PatchMapping("/search")
    public ResponseEntity<ApiResponse> getSearchAutoCompletePage(
            @RequestParam String name,
            @RequestParam int page
    ) {
        return ChannelControllerUtils
                .handleRequest(() -> channelService.channelSearchAutoCompleteDto(name, page));
    }
    @GetMapping("/recommend/{category}")
    public ResponseEntity<ApiResponse> getRecommendedChannels(@PathVariable String category) {
        return ChannelControllerUtils
                .handleRequest(() -> channelService.loadRecommendedChannelsByCategory(category));
    }
    @PutMapping
    public ResponseEntity<ApiResponse> updateChannelName(
            @RequestBody ChannelUpdateDto channel,
            @RequestHeader("Authorization") String token
    ) {
        return ChannelControllerUtils.handleRequest(() -> {
            final long ownerId = jwtUtil.extractUser(token).id();
            return channelService.updateChannelName(channel.newName(),channel.id(),ownerId);
        });
    }
}