package com.example.channelmicroservice.controller;

import com.example.bussiness.model.ApiResponse;
import com.example.channelmicroservice.bussiness.exception.ChannelException;
import com.example.channelmicroservice.controller.model.ChannelDto;
import com.example.channelmicroservice.controller.model.ChannelSearchAutoCompleteDto;
import com.example.channelmicroservice.domain.bussiness.ChannelService;
import com.example.channelmicroservice.domain.entity.ChannelEntity;
import com.example.channelmicroservice.controller.extensions.ChannelMapper;
import com.example.security.model.JwtSecurityModel;
import com.example.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
            ChannelEntity channel = channelService.loadChannelById(id);
            return new ResponseEntity<>(
                    new ApiResponse.Success<>(channel),
                    HttpStatus.OK
            );
        } catch (ChannelException e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("404", e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("403", "data not valid"),
                    HttpStatus.NOT_FOUND
            );
        }
    }
//1989 6813
    @PostMapping
    public ResponseEntity<ApiResponse> addChannel(
            @RequestBody ChannelDto channelDto,
            @RequestHeader("Authorization") String token
    ) {
        JwtSecurityModel user = jwtUtil.extractUser(token);
        try {
            channelService.addChannel(channelDto,user.id());
            return new ResponseEntity<>(
                    new ApiResponse.Success<>(channelDto),
                    HttpStatus.OK
            );
        } catch (ChannelException e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("400", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("403", "data not valid"),
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> loadAllChannels() {
        try {
            List<ChannelDto> channels = channelService.loadAllChannels();
            return new ResponseEntity<>(
                    new ApiResponse.Success<>(channels),
                    HttpStatus.OK
            );
        } catch (ChannelException e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("404", e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("403", "data not valid"),
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<ApiResponse> loadChannelByOwnerId(@PathVariable long id) {
        try {
            List<ChannelDto> channelResponse = channelService.loadAllChannelsByOwnerId(id);
            return new ResponseEntity<>(
                    new ApiResponse.Success<>(channelResponse),
                    HttpStatus.OK
            );
        } catch (ChannelException e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("404", e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("403", "data not valid"),
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> removeChannelById(@PathVariable long id) {
        var isDeleted = channelService.removeChannelById(id);

        if (!isDeleted) return new ResponseEntity<>(
                new ApiResponse.Error("404", "No Channel Found"),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(
                new ApiResponse.Success<>(isDeleted),
                HttpStatus.OK
        );
    }

    @PatchMapping("/search")
    public ResponseEntity<ApiResponse> getSearchAutoCompletePage(@RequestParam String name, @RequestParam int page) {
        try {
            var channelListDto = channelService.channelSearchAutoCompleteDto(name, page);
            return new ResponseEntity<>(
                    new ApiResponse.Success<>(channelListDto),
                    HttpStatus.OK
            );
        } catch (ChannelException e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("404", e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("403", "data not valid"),
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @GetMapping("/recommend/{category}")
    public ResponseEntity<ApiResponse> getRecommendedChannels(@PathVariable String category) {
        try {
            var channelListDto = channelService.loadRecommendedChannelsByCategory(category);
            return new ResponseEntity<>(
                    new ApiResponse.Success<>(channelListDto),
                    HttpStatus.OK
            );
        } catch (ChannelException e) {
           return new ResponseEntity<>(
                    new ApiResponse.Error("404", e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse.Error("403", "data not valid"),
                    HttpStatus.FORBIDDEN
            );
        }
    }
}
