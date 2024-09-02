package com.example.channelmicroservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "channel")
public class ChannelEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "owner_id")
    private long ownerId;
    @Basic
    @Column(name = "subscribers_count")
    private int subscribersCount;
    @Basic
    @Column(name = "category")
    private String category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelEntity that = (ChannelEntity) o;
        return id == that.getId() && ownerId == that.getOwnerId() && subscribersCount == that.getSubscribersCount() && Objects.equals(name, that.getName()) && Objects.equals(category, that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ownerId, subscribersCount, category);
    }
}
