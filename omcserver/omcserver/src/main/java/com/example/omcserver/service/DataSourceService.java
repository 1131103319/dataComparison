package com.example.omcserver.service;

import com.example.omcserver.model.Alarm;

import java.util.List;

public interface DataSourceService {

    void addAlarm(Alarm alarm,String bussiness);
    int getMaxId();
    //todo 获取所有需要告警的信息
    List<Alarm> listAlarm();
    //todo 获取大于等于id的告警
    List<Alarm> listAlarm(int id);
    //todo 获取指定时间范围的告警
    public List<Alarm> listAlarm(String startTime, String endTime,String syncSource);
    //todo 标记已经获取的告警
    void updateStatus();
    //todo 更新流水时间
    void updateFlowTime(String formatTime,String alarmSeq);
    //todo 重启恢复异常状态
    void updateErrorStatus();
    void get4Gmdn(String startTime,String endTime,String currentTime);
    void get4Gsourceip(String startTime,String endTime,String currentTime);
    void get5Gmdn(String startTime,String endTime,String currentTime);
    void get5Gsourceip(String startTime,String endTime,String currentTime);
    void getHomeaccount(String startTime,String endTime,String currentTime);
    void getHomesourceip(String startTime,String endTime,String currentTime);
    void getIdcsourceip(String startTime,String endTime,String currentTime);
    List<Integer> getAlarmSeq(String alarmTitle);
    void updateAlarmed(String bussiness,String alarmId);
    //todo 获取到业务当前最新状态 0清除，1告警
    Integer getAlarmStatus(String bussiness);
}