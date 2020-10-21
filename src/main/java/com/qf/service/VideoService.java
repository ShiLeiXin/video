package com.qf.service;

import com.qf.pojo.QueryVo;
import com.qf.pojo.Video;

import java.util.List;

public interface VideoService {
    public List<Video> findAllVideoByQueryVo(QueryVo queryVo);
    public Integer getTotalCounts();
    public void addVideo(Video video);
    public Video findVideoById(Integer id);
    public void updateVideo(Video video);
    public Integer deleteVideo(Integer id);
}
