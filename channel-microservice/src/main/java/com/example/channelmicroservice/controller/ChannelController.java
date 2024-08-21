package com.example.channelmicroservice.controller;

import com.example.api.ApiResponse;
import com.example.channelmicroservice.bussiness.ChannelService;
import com.example.channelmicroservice.controller.model.ChannelDto;
import com.example.channelmicroservice.mapper.ChannelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping(value = "/channel")
@RequiredArgsConstructor
@RestController
public class ChannelController {
    private final ChannelService channelService;

    @GetMapping("/{id}")
    public ResponseEntity<?> loadChannelById(@PathVariable long id) {
        ChannelDto channelResponse = channelService.loadChannelById(id);
        if (channelResponse == null) return new ResponseEntity<>(
                new ApiResponse.Error("404", "No Channel Found"),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(
                new ApiResponse.Success<>(channelResponse),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> addChannel(@RequestBody ChannelDto channelDto) {
        var channelEntity = channelService.addChannel(
                ChannelMapper.mapToChannelEntity(channelDto)
        );

        if (channelEntity == null) return new ResponseEntity<>(
                new ApiResponse.Error("404", "No Channel Found"),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(
                new ApiResponse.Success<>(channelEntity),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> loadAllChannels() {
        var channels = channelService.loadAllChannels();

        if (channels == null) return new ResponseEntity<>(
                new ApiResponse.Error("404", "No Channel Found"),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(
                new ApiResponse.Success<>(channels),
                HttpStatus.OK
        );
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<?> loadChannelByOwnerId(@PathVariable long id) {
        var channelResponse = channelService.loadAllChannelsByOwnerId(id);

        if (channelResponse == null) return new ResponseEntity<>(
                new ApiResponse.Error("404", "No Channel Found"),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(
                new ApiResponse.Success<>(channelResponse),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeChannelById(@PathVariable long id) {
        var isDeleted = channelService.removeChannelById(id);

        if (!isDeleted) new ResponseEntity<>(
                new ApiResponse.Error("404", "No Channel Found"),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(
                new ApiResponse.Success<>(isDeleted),
                HttpStatus.OK
        );
    }
}
