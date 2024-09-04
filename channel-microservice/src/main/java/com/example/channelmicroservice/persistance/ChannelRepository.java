package com.example.channelmicroservice.persistance;

import com.example.channelmicroservice.domain.entity.ChannelEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {
    List<ChannelEntity> findAllByOwnerId(long ownerId);
    List<ChannelEntity> findAllByCategory(String category, Limit limit);
    ChannelEntity findFirstByName(String name);
    @Query("FROM ChannelEntity where name like concat(:name,'%') ")
    List<ChannelEntity> searchAllByName(String name);
}