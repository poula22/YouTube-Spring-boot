package com.example.channelmicroservice.controller;

import com.example.bussiness.model.ApiResponse;
import com.example.channelmicroservice.controller.model.ChannelDto;
import com.example.channelmicroservice.controller.utils.ChannelControllerUtils;
import com.example.channelmicroservice.domain.bussiness.ChannelService;
import com.example.security.model.JwtSecurityModel;
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
        return ChannelControllerUtils.handleRequest(() -> channelService.loadChannelById(id));
    }
    @PostMapping
    public ResponseEntity<ApiResponse> addChannel(
            @RequestBody ChannelDto channelDto,
            @RequestHeader("Authorization") String token
    ) {
        return ChannelControllerUtils.handleRequest(() -> {
            JwtSecurityModel user = jwtUtil.extractUser(token);
            channelService.addChannel(channelDto, user.id());
            return channelDto;
        });
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> loadAllChannels() {
        return ChannelControllerUtils.handleRequest(channelService::loadAllChannels);
    }
    @GetMapping("/owner/{id}")
    public ResponseEntity<ApiResponse> loadChannelByOwnerId(@PathVariable long id) {
        return ChannelControllerUtils
                .handleRequest(() -> channelService.loadAllChannelsByOwnerId(id));
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
}