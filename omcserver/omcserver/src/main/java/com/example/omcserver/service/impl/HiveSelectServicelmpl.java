package com.example.omcserver.service.impl;

import com.example.omcserver.service.HiveSelectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class HiveSelectServicelmpl implements HiveSelectService {
    @Autowired
    DataSourceServicelmpl dataSourceServicelmpl;

    @Scheduled(cron = "${scheduled.cron.expression}")
    public void processDataBatch() {
        try {
            String beforTime = getBeforTime();
            String afterTime = getAfterTime();
            String currentTime = getCurrentTime();
            log.info("start select 填充率,{}-{}-{}", beforTime, afterTime, currentTime);
            dataSourceServicelmpl.get4Gmdn(beforTime, afterTime,currentTime);
            dataSourceServicelmpl.get4Gsourceip(beforTime, afterTime,currentTime);
            dataSourceServicelmpl.get5Gmdn(beforTime, afterTime,currentTime);
            dataSourceServicelmpl.get5Gsourceip(beforTime, afterTime,currentTime);
            dataSourceServicelmpl.getHomeaccount(beforTime, afterTime,currentTime);
            dataSourceServicelmpl.getHomesourceip(beforTime, afterTime,currentTime);
            dataSourceServicelmpl.getIdcsourceip(beforTime, afterTime,currentTime);
        } catch (Exception e) {
            log.error("查询异常", e);
        }
    }

    public String getBeforTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime localDateTime = currentTime.minusHours(4);
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        // 格式化当前时间
        String formattedTime = localDateTime.format(formatter) + ":02:00";
        return formattedTime;
    }
    public String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        // 格式化当前时间
        String formattedTime = currentTime.format(formatter) + ":02:00";
        return formattedTime;
    }

    public String getAfterTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime localDateTime = currentTime.minusHours(3);
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        // 格式化当前时间
        String formattedTime = localDateTime.format(formatter) + ":00:00";
        return formattedTime;
    }
}
