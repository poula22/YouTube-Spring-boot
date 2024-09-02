package com.example.channelmicroservice.persistance;

import com.example.channelmicroservice.domain.entity.ChannelEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelPaginationRepository
        extends PagingAndSortingRepository<ChannelEntity, Long> {
    @Query("from ChannelEntity where category like concat(:category,'%') ")
    List<ChannelEntity> findChannelEntitiesByCategory(@Param("category") String category, Pageable pageable);
}
