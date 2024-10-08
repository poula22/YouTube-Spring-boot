package com.example.channelmicroservice.bussiness;

import com.example.channelmicroservice.domain.bussiness.exception.ChannelException;
import com.example.channelmicroservice.domain.bussiness.mapper.ChannelMapper;
import com.example.channelmicroservice.domain.bussiness.model.ChannelRequestDto;
import com.example.channelmicroservice.domain.bussiness.model.ChannelResponseDto;
import com.example.channelmicroservice.domain.bussiness.model.ChannelSearchAutoCompleteDto;
import com.example.channelmicroservice.domain.bussiness.ChannelService;
import com.example.channelmicroservice.domain.entity.ChannelEntity;
import com.example.channelmicroservice.persistance.ChannelPaginationRepository;
import com.example.channelmicroservice.persistance.ChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChannelServiceImp implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelPaginationRepository channelPaginationRepository;

    @Override
    public ChannelEntity loadChannelById(long id) throws ChannelException {
        var channel = channelRepository.findById(id).orElse(null);
        if (channel == null) throw new ChannelException(404, "channel not found");
        return channel;
    }

    @Override
    public ChannelResponseDto addChannel(ChannelRequestDto channel, long ownerId) throws ChannelException {
        try {
            ChannelEntity entity = ChannelMapper.INSTANCE.fromDtoToEntity(channel, ownerId);
            log.info(entity.toString());
            channelRepository.save(entity);
            return ChannelMapper.INSTANCE.fromEntityToDto(entity);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ChannelException(400, "channel already exist");
        }
    }

    @Override
    public ChannelResponseDto updateChannelName(String newName, long channelId, long ownerId) throws ChannelException {
        ChannelEntity entity = channelRepository.findFirstByName(newName);
        if (entity != null) throw new ChannelException(400, "Name already exist");

        Optional<ChannelEntity> oldEntityOptional = channelRepository.findById(channelId);
        ChannelEntity updatedEntity = oldEntityOptional.map((oldEntity) -> {
            oldEntity.setName(newName);
            return oldEntity;
        }).orElseThrow(() -> new ChannelException(400, "Bad Operation"));

        if (updatedEntity.getOwnerId() != ownerId) throw new ChannelException(401, "unAuthorized");
        try {
            channelRepository.save(updatedEntity);
            return ChannelMapper.INSTANCE.fromEntityToDto(updatedEntity);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ChannelException(400, "something went wrong");
        }
    }

    @Override
    public List<ChannelResponseDto> loadAllChannels() throws ChannelException {
        var channels = channelRepository.findAll();

        if (channels.isEmpty()) throw new ChannelException(404, "No Channel Found");

        return channels
                .stream()
                .map(ChannelMapper.INSTANCE::fromEntityToDto)
                .toList();
    }

    @Override
    public List<ChannelResponseDto> loadAllChannelsByOwnerId(long ownerId) throws ChannelException {
        final List<ChannelEntity> channels = channelRepository.findAllByOwnerId(ownerId);
        if (channels.isEmpty()) throw new ChannelException(200, "user doesn't have any channel yet");
        return channels.stream()
                .map(ChannelMapper.INSTANCE::fromEntityToDto)
                .toList();
    }

    @Override
    public List<ChannelSearchAutoCompleteDto> loadAllChannelsByOwnerName(String ownerName) throws ChannelException {
        var channels = channelRepository.searchAllByName(ownerName);
        if (channels.isEmpty()) throw new ChannelException(200, "user doesn't have any channel yet");
        return channels.stream()
                .map(channelEntity -> new ChannelSearchAutoCompleteDto(
                        channelEntity.getId(),
                        channelEntity.getCategory()
                ))
                .toList();
    }

    @Override
    public void removeChannelById(long id) throws ChannelException {
        try {
            channelRepository.deleteById(id);
        } catch (Exception e) {
            throw new ChannelException(400, "user doesn't have access to this channel");
        }
    }

    @Override
    public List<ChannelSearchAutoCompleteDto> channelSearchAutoCompleteDto(String name, int page)
            throws ChannelException {
        int pageSize = 5;
        log.info(name);
        var channels = channelPaginationRepository
                .findChannelEntitiesByCategory(
                        name,
                        Pageable.ofSize(pageSize).withPage(page)
                ).stream().map(channelEntity -> new ChannelSearchAutoCompleteDto(
                        channelEntity.getId(),
                        channelEntity.getCategory()
                )).toList();

        if (channels.isEmpty()) throw new ChannelException(200, "No result to show");
        return channels;
    }

    @Override
    public List<ChannelSearchAutoCompleteDto> loadRecommendedChannelsByCategory(String category) throws ChannelException {
        var channels = channelRepository.findAllByCategory(category, Limit.of(5));

        if (channels.isEmpty()) throw new ChannelException(200, "no channels to show");

        return channels.stream()
                .map(channelEntity -> new ChannelSearchAutoCompleteDto(
                        channelEntity.getId(),
                        channelEntity.getCategory()
                ))
                .toList();
    }
}