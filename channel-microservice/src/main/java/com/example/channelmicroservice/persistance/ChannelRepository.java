package com.example.channelmicroservice.persistance;

import com.example.channelmicroservice.domain.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {
    List<ChannelEntity> findChannelsByOwnerId(long ownerId);
}
