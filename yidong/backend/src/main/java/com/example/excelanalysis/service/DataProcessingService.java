package com.example.excelanalysis.service;

import com.example.excelanalysis.dto.ExcelDataDTO;
import com.example.excelanalysis.model.CalculatedData;
import com.example.excelanalysis.model.SourceData;
import com.example.excelanalysis.repository.CalculatedDataRepository;
import com.example.excelanalysis.repository.SourceDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DataProcessingService {

    @Autowired
    private SourceDataRepository sourceDataRepository;

    @Autowired
    private CalculatedDataRepository calculatedDataRepository;

    @Transactional
    public void processExcelData(List<ExcelDataDTO> excelDataList) {
        for (ExcelDataDTO excelData : excelDataList) {
            //如果有重复的就删除重新处理
            calculatedDataRepository.deleteByTimeAndBusinessType(excelData.getTime(), excelData.getBusinessType());
            // 根据业务类型和日期从source_data表查询数据
            List<SourceData> sourceDataList = sourceDataRepository.findById_BusinessTypeAndId_Time(
                    excelData.getBusinessType(),
                    excelData.getTime()
            );
            if (!sourceDataList.isEmpty()) {
                // 对于同一天同一业务类型的多条记录，我们需要合并处理
                SourceData mergedSourceData = mergeSourceData(sourceDataList);
                // 将合并后的源数据和Excel数据整合到calculated_data表中
                CalculatedData calculatedData = calculateData(mergedSourceData, excelData);
                calculatedDataRepository.save(calculatedData);
            } else {
                // 如果没有找到对应的源数据，记录日志
                log.warn("未找到对应的源数据: 业务类型=" + excelData.getBusinessType() + ", 时间=" + excelData.getTime());
                System.out.println("未找到对应的源数据: 业务类型=" + excelData.getBusinessType() + ", 时间=" + excelData.getTime());
            }
        }
    }

    /**
     * 合并同一天同一业务类型的多条源数据记录
     *
     * @param sourceDataList 源数据列表
     * @return 合并后的源数据
     */
    private SourceData mergeSourceData(List<SourceData> sourceDataList) {
        SourceData mergedData = new SourceData();

        // 使用第一条记录的基本信息
        SourceData firstData = sourceDataList.get(0);
        mergedData.getId().setBusinessType(firstData.getId().getBusinessType());
        mergedData.setNetworkType(firstData.getNetworkType());
        mergedData.getId().setTime(firstData.getId().getTime());

        // 累加所有数值字段
        Long receivedDataCount = 0L;
        Long receivedFileCount = 0L;
        Long totalFileCount = 0L;
        Long phoneNullCount = 0L;
        Long domainNullCount = 0L;
        Long destIpNullCount = 0L;
        Long destPortNullCount = 0L;
        Long sourceIpNullCount = 0L;
        Long sourcePortNullCount = 0L;
        Long protocolNullCount = 0L;

        for (SourceData data : sourceDataList) {
            if (data.getReceivedDataCount() != null) receivedDataCount += data.getReceivedDataCount();
            else receivedDataCount = null;
            if (data.getReceivedFileCount() != null) receivedFileCount += data.getReceivedFileCount();
            else receivedFileCount = null;
            if (data.getTotalFileCount() != null) totalFileCount += data.getTotalFileCount();
            else totalFileCount = null;
            if (data.getPhoneNullCount() != null) phoneNullCount += data.getPhoneNullCount();
            else phoneNullCount = null;
            if (data.getDomainNullCount() != null) domainNullCount += data.getDomainNullCount();
            else domainNullCount = null;
            if (data.getDestIpNullCount() != null) destIpNullCount += data.getDestIpNullCount();
            else destIpNullCount = null;
            if (data.getDestPortNullCount() != null) destPortNullCount += data.getDestPortNullCount();
            else destPortNullCount = null;
            if (data.getSourceIpNullCount() != null) sourceIpNullCount += data.getSourceIpNullCount();
            else sourceIpNullCount = null;
            if (data.getSourcePortNullCount() != null) sourcePortNullCount += data.getSourcePortNullCount();
            else sourcePortNullCount = null;
            if (data.getProtocolNullCount() != null) protocolNullCount += data.getProtocolNullCount();
            else protocolNullCount = null;
        }

        // 设置合并后的值
        mergedData.setReceivedDataCount(receivedDataCount);
        mergedData.setReceivedFileCount(receivedFileCount);
        mergedData.setTotalFileCount(totalFileCount);
        mergedData.setPhoneNullCount(phoneNullCount);
        mergedData.setDomainNullCount(domainNullCount);
        mergedData.setDestIpNullCount(destIpNullCount);
        mergedData.setDestPortNullCount(destPortNullCount);
        mergedData.setSourceIpNullCount(sourceIpNullCount);
        mergedData.setSourcePortNullCount(sourcePortNullCount);
        mergedData.setProtocolNullCount(protocolNullCount);
        return mergedData;
    }

    private CalculatedData calculateData(SourceData source, ExcelDataDTO excel) {
        CalculatedData calculated = new CalculatedData();

        // 从source_data表获取的数据
        calculated.setBusinessType(source.getId().getBusinessType());  // 业务类型
        calculated.setNetworkType(source.getNetworkType());   // 网络类型
        calculated.setTime(source.getId().getTime());  // 时间
        calculated.setReceivedCount(source.getReceivedDataCount());     // 接收数据量
        calculated.setReceivedFileCount(source.getReceivedFileCount()); // 接收文件量

        // 从Excel获取的数据（入库量）
        calculated.setTotalDataCount(excel.getTotalDataCount());     // 入库数据量
        calculated.setTotalFileCount(excel.getTotalFileCount());     // 入库文件量

        // 计算回填率 = 空值数量/接收条数
        if (source.getReceivedDataCount() != null && source.getReceivedDataCount() > 0) {
            calculated.setReceivedRate(calculateRate(source.getTotalFileCount(), calculated.getTotalFileCount()));
            calculated.setInboundRate(calculateRate(source.getReceivedFileCount(), source.getTotalFileCount()));
            calculated.setPhoneNullRate(calculateRate(source.getPhoneNullCount(), source.getReceivedDataCount()));
            calculated.setDomainNullRate(calculateRate(source.getDomainNullCount(), source.getReceivedDataCount()));
            calculated.setDestIpNullRate(calculateRate(source.getDestIpNullCount(), source.getReceivedDataCount()));
            calculated.setDestPortNullRate(calculateRate(source.getDestPortNullCount(), source.getReceivedDataCount()));
            calculated.setSourceIpNullRate(calculateRate(source.getSourceIpNullCount(), source.getReceivedDataCount()));
            calculated.setSourcePortNullRate(calculateRate(source.getSourcePortNullCount(), source.getReceivedDataCount()));
            calculated.setProtocolNullRate(calculateRate(source.getProtocolNullCount(), source.getReceivedDataCount()));
        }
        return calculated;
    }

    private String calculateRate(Long nullCount, Long totalCount) {
        return (nullCount == null) ? "-" : String.format("%.2f", (double) nullCount / totalCount * 100);
    }
}