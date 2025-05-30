package com.example.omcserver.service;

import com.example.omcserver.model.Alarm;

import java.util.List;

public interface DataSourceService {

    void addAlarm(Alarm alarm,String bussiness);
    int getMaxId();
    List<Alarm> listAlarm();

    List<Alarm> listAlarm(int id);

    public List<Alarm> listAlarm(String startTime, String endTime);

    void updateStatus();
    void get4Gmdn(String startTime,String endTime);
    void get4Gsourceip(String startTime,String endTime);
    void get5Gmdn(String startTime,String endTime);
    void get5Gsourceip(String startTime,String endTime);
    void getHomeaccount(String startTime,String endTime);
    void getHomesourceip(String startTime,String endTime);
    void getIdcsourceip(String startTime,String endTime);
    List<Integer> getAlarmSeq(String alarmTitle);
    void updateAlarmed(String bussiness,String alarmId);
}