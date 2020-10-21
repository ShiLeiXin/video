package com.qf.service.impl;

import com.qf.dao.SpeakerMapper;
import com.qf.pojo.Speaker;
import com.qf.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeakerServiceImpl implements SpeakerService {

    @Autowired
    private SpeakerMapper speakerMapper;


    @Override
    public List<Speaker> findAllSpeakers() {
        return speakerMapper.selectByExampleWithBLOBs(null);
    }

    @Override
    public Integer getTotalCounts() {
        return speakerMapper.countByExample(null);
    }

    @Override
    public Speaker findSpeakerById(Integer id) {
        return speakerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addSpeaker(Speaker speaker) {
        speakerMapper.insertSelective(speaker);
    }

    @Override
    public void updateSpeaker(Speaker speaker) {
        speakerMapper.updateByPrimaryKeySelective(speaker);
    }

    @Override
    public Integer deleteSpeakerById(Integer id) {
        return speakerMapper.deleteByPrimaryKey(id);
    }
}
