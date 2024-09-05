package com.example.channelmicroservice.domain.bussiness;

import com.example.channelmicroservice.domain.bussiness.exception.ChannelException;
import com.example.channelmicroservice.domain.bussiness.model.ChannelRequestDto;
import com.example.channelmicroservice.domain.bussiness.model.ChannelResponseDto;
import com.example.channelmicroservice.domain.bussiness.model.ChannelSearchAutoCompleteDto;
import com.example.channelmicroservice.domain.entity.ChannelEntity;

import java.util.List;

public interface ChannelService {
    ChannelEntity loadChannelById(long id) throws ChannelException;
    ChannelResponseDto addChannel(ChannelRequestDto channel, long ownerId) throws ChannelException;
    ChannelResponseDto updateChannelName(String newName, long channelId, long ownerId) throws ChannelException;
    List<ChannelResponseDto> loadAllChannels() throws ChannelException;
    List<ChannelResponseDto> loadAllChannelsByOwnerId(long ownerId) throws ChannelException;
    List<ChannelSearchAutoCompleteDto> loadAllChannelsByOwnerName(String ownerName) throws ChannelException;
    void removeChannelById(long id) throws ChannelException;
    List<ChannelSearchAutoCompleteDto> channelSearchAutoCompleteDto(String name, int  page) throws ChannelException;
    List<ChannelSearchAutoCompleteDto> loadRecommendedChannelsByCategory(String category) throws ChannelException;
}
