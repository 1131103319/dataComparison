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
            log.info("start select 填充率,{}-{}", beforTime, afterTime);
            dataSourceServicelmpl.get4Gmdn(beforTime, afterTime);
            dataSourceServicelmpl.get4Gsourceip(beforTime, afterTime);
            dataSourceServicelmpl.get5Gmdn(beforTime, afterTime);
            dataSourceServicelmpl.get5Gsourceip(beforTime, afterTime);
            dataSourceServicelmpl.getHomeaccount(beforTime, afterTime);
            dataSourceServicelmpl.getHomesourceip(beforTime, afterTime);
            dataSourceServicelmpl.getIdcsourceip(beforTime, afterTime);
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
