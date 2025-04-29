package com.example.excelanalysis.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class DataStatisticsResponse {
    private String date;
    private String networkType;
    private List<String> businessTypes;
    private Map<String, Long> totalDataCounts;
    private Map<String, Long> receivedCounts;
    private Map<String, Long> totalFileCounts;
    private Map<String, Long> receivedFileCounts;
    private Map<String, Long> receivedRate;
    private Map<String, Long> inboundRate;
    private Map<String, String> phoneNullRates;
    private Map<String, String> domainNullRates;
    private Map<String, String> destIpNullRates;
    private Map<String, String> destPortNullRates;
    private Map<String, String> sourceIpNullRates;
    private Map<String, String> sourcePortNullRates;
    private Map<String,String> protocolNullRates;
    private Map<String, Object> responseMap;  // 新增字段，用于存储前端所需的格式化数据
}