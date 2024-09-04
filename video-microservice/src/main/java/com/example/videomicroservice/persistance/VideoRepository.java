package com.example.videomicroservice.persistance;

import com.example.videomicroservice.domain.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntity,Long> {
    @Query("FROM VideoEntity where title like concat(:title,'%') ")
    List<VideoEntity> searchAllByTitle(String title);
}
