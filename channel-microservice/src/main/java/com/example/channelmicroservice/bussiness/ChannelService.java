package com.example.channelmicroservice.bussiness;

import com.example.channelmicroservice.controller.model.ChannelDto;
import com.example.channelmicroservice.domain.ChannelEntity;
import com.example.channelmicroservice.mapper.ChannelMapper;
import com.example.channelmicroservice.persistance.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChannelService {
    private final ChannelRepository channelRepository;

    public ChannelDto loadChannelById(long id) {
        Optional<ChannelEntity> channelEntity = channelRepository.findById(id);
        return channelEntity
                .map(ChannelMapper::mapToChannelDto)
                .orElse(null);
    }
    public ChannelDto addChannel(ChannelEntity channel) {
        try {
            channelRepository.save(channel);
            return ChannelMapper.mapToChannelDto(channel);
        } catch (Exception e) {
            return null;
        }
    }
    public List<ChannelDto> loadAllChannels() {
        return channelRepository.findAll()
                .stream()
                .map(ChannelMapper::mapToChannelDto)
                .collect(Collectors.toList());
    }
    public List<ChannelDto> loadAllChannelsByOwnerId(long ownerId) {
        return channelRepository.findChannelsByOwnerId(ownerId)
                .stream()
                .map(ChannelMapper::mapToChannelDto)
                .collect(Collectors.toList());
    }
    public Boolean removeChannelById(long id) {
        try {
            channelRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

}
