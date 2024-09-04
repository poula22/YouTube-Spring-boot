package com.example.videomicroservice.domain.bussiness;

import com.example.videomicroservice.domain.bussiness.exception.VideoException;
import com.example.videomicroservice.domain.bussiness.model.VideoDto;

import java.util.List;

public interface VideoService {
    VideoDto findVideoById(long id) throws VideoException;
    List<VideoDto> findAllVideosByName(String name) throws VideoException;
    VideoDto addVideo(VideoDto videoDto) throws VideoException;
}
