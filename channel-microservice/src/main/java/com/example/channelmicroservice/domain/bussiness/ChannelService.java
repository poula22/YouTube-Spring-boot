package com.example.channelmicroservice.domain.bussiness;

import com.example.channelmicroservice.bussiness.exception.ChannelException;
import com.example.channelmicroservice.controller.model.ChannelDto;
import com.example.channelmicroservice.controller.model.ChannelSearchAutoCompleteDto;
import com.example.channelmicroservice.domain.entity.ChannelEntity;

import java.util.List;

public interface ChannelService {
    ChannelEntity loadChannelById(long id) throws ChannelException;
    ChannelDto addChannel(ChannelDto channel,long ownerId) throws ChannelException;
    List<ChannelDto> loadAllChannels() throws ChannelException;
    List<ChannelDto> loadAllChannelsByOwnerId(long ownerId) throws ChannelException;
    Boolean removeChannelById(long id);
    List<ChannelSearchAutoCompleteDto> channelSearchAutoCompleteDto(String name, int  page) throws ChannelException;
    List<ChannelDto> loadRecommendedChannelsByCategory(String category) throws ChannelException;
}
