package com.qf.service;

import com.qf.pojo.Speaker;

import java.util.List;

public interface SpeakerService {
    public List<Speaker> findAllSpeakers();
    public Integer getTotalCounts();
    public Speaker findSpeakerById(Integer id);
    public void addSpeaker(Speaker speaker);
    public void updateSpeaker(Speaker speaker);
    public Integer deleteSpeakerById(Integer id);
}
