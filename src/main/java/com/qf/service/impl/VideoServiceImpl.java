package com.qf.service.impl;

import com.qf.dao.VideoMapper;
import com.qf.pojo.QueryVo;
import com.qf.pojo.Video;
import com.qf.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAllVideoByQueryVo(QueryVo queryVo) {
       return videoMapper.findAllByQueryVo(queryVo);
    }

    @Override
    public Integer getTotalCounts() {
        return videoMapper.countByExample(null);
    }

    @Override
    public void addVideo(Video video) {
        videoMapper.insertSelective(video);
    }

    @Override
    public Video findVideoById(Integer id) {

        return videoMapper.findVideoById(id);
    }

    @Override
    public void updateVideo(Video video) {
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    public Integer deleteVideo(Integer id) {
        return videoMapper.deleteByPrimaryKey(id);
    }
}
