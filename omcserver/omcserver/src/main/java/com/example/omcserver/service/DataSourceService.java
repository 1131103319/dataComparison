package com.example.omcserver.service;

import com.example.omcserver.model.Alarm;

import java.util.List;

public interface DataSourceService {

    void addUser(Alarm alarm);

    List<Alarm> listAlarm();

    List<Alarm> listAlarm(int id);

    public List<Alarm> listAlarm(String startTime, String endTime);

    Integer updateStatus();
}