package com.example.videomicroservice.bussiness;

import com.example.videomicroservice.domain.bussiness.VideoService;
import com.example.videomicroservice.domain.bussiness.exception.VideoException;
import com.example.videomicroservice.domain.bussiness.mapper.VideoMapper;
import com.example.videomicroservice.domain.bussiness.model.VideoDto;
import com.example.videomicroservice.domain.entity.VideoEntity;
import com.example.videomicroservice.persistance.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VideoServiceImp implements VideoService {
    private final VideoRepository videoRepository;

    @Override
    public VideoDto findVideoById(long id) throws VideoException {
        return videoRepository.findById(id)
                .map(VideoMapper.INSTANCE::fromEntityToDto)
                .orElseThrow(() -> new VideoException(400, "id is not valid"));
    }
    @Override
    public List<VideoDto> findAllVideosByName(String name) throws VideoException {
        final List<VideoEntity> videoEntityList = videoRepository.searchAllByTitle(name);
        if (videoEntityList.isEmpty()) throw new VideoException(200,"No videos to show");
        return videoEntityList
                .parallelStream()
                .map(VideoMapper.INSTANCE::fromEntityToDto)
                .toList();
    }
    @Override
    public VideoDto addVideo(VideoDto videoDto) throws VideoException {
        //send video to server and get url
        String date = Instant.now().toString();
        VideoEntity videoEntity = VideoMapper.INSTANCE.fromDtoToEntity(videoDto,date);
        try {
            videoRepository.save(videoEntity);
        } catch (Exception e) {
            throw new VideoException(400,"Can't upload this video to channel");
        }
        return videoDto;
    }
}
