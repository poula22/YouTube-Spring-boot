package com.example.channelmicroservice.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "video")
public class VideoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "channel_id")
    private long channelId;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "url")
    private String url;
    @Basic
    @Column(name = "describtion")
    private String describtion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoEntity that = (VideoEntity) o;
        return id == that.id && channelId == that.channelId && Objects.equals(title, that.title) && Objects.equals(url, that.url) && Objects.equals(describtion, that.describtion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, title, url, describtion);
    }
}
