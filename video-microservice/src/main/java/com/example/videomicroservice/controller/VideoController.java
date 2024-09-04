package com.example.videomicroservice.controller;

import com.example.util.JwtUtil;
import com.example.videomicroservice.domain.bussiness.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final JwtUtil jwtUtil = new JwtUtil();

}
