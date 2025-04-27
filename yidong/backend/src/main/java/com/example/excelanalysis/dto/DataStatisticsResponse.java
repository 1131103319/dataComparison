package com.example.excelanalysis.dto;

import java.util.List;
import java.util.Map;

public class DataStatisticsResponse {
    private String date;
    private String networkType;
    private List<String> businessTypes;
    private Map<String, Long> totalDataCounts;
    private Map<String, Long> receivedCounts;
    private Map<String, Long> totalFileCounts;
    private Map<String, Long> receivedFileCounts;
    private Map<String, String> phoneNullRates;
    private Map<String, String> domainNullRates;
    private Map<String, String> destIpNullRates;
    private Map<String, String> destPortNullRates;
    private Map<String, String> sourceIpNullRates;
    private Map<String, String> sourcePortNullRates;
    private Map<String,String> protocolNullRates;
    private Map<String, Object> responseMap;  // 新增字段，用于存储前端所需的格式化数据


    public Map<String, Object> getResponseMap() {
        return responseMap;
    }

    public void setResponseMap(Map<String, Object> responseMap) {
        this.responseMap = responseMap;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public List<String> getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(List<String> businessTypes) {
        this.businessTypes = businessTypes;
    }

    public Map<String, Long> getTotalDataCounts() {
        return totalDataCounts;
    }

    public void setTotalDataCounts(Map<String, Long> totalDataCounts) {
        this.totalDataCounts = totalDataCounts;
    }

    public Map<String, Long> getReceivedCounts() {
        return receivedCounts;
    }

    public void setReceivedCounts(Map<String, Long> receivedCounts) {
        this.receivedCounts = receivedCounts;
    }

    public Map<String, Long> getTotalFileCounts() {
        return totalFileCounts;
    }

    public void setTotalFileCounts(Map<String, Long> totalFileCounts) {
        this.totalFileCounts = totalFileCounts;
    }

    public Map<String, Long> getReceivedFileCounts() {
        return receivedFileCounts;
    }

    public void setReceivedFileCounts(Map<String, Long> receivedFileCounts) {
        this.receivedFileCounts = receivedFileCounts;
    }

    public Map<String, String> getPhoneNullRates() {
        return phoneNullRates;
    }

    public void setPhoneNullRates(Map<String, String> phoneNullRates) {
        this.phoneNullRates = phoneNullRates;
    }

    public Map<String, String> getDomainNullRates() {
        return domainNullRates;
    }

    public void setDomainNullRates(Map<String, String> domainNullRates) {
        this.domainNullRates = domainNullRates;
    }

    public Map<String, String> getDestIpNullRates() {
        return destIpNullRates;
    }

    public void setDestIpNullRates(Map<String, String> destIpNullRates) {
        this.destIpNullRates = destIpNullRates;
    }

    public Map<String, String> getDestPortNullRates() {
        return destPortNullRates;
    }

    public void setDestPortNullRates(Map<String, String> destPortNullRates) {
        this.destPortNullRates = destPortNullRates;
    }

    public Map<String, String> getSourceIpNullRates() {
        return sourceIpNullRates;
    }

    public void setSourceIpNullRates(Map<String, String> sourceIpNullRates) {
        this.sourceIpNullRates = sourceIpNullRates;
    }

    public Map<String, String> getSourcePortNullRates() {
        return sourcePortNullRates;
    }

    public void setSourcePortNullRates(Map<String, String> sourcePortNullRates) {
        this.sourcePortNullRates = sourcePortNullRates;
    }
    public Map<String, String> getProtocolNullRates() {
        return protocolNullRates;
    }

    public void setProtocolNullRates(Map<String, String> protocolNullRates) {
        this.protocolNullRates = protocolNullRates;
    }
}