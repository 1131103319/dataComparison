package com.example.excelanalysis.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "calculated_data")
public class CalculatedData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "business_type")
    private String businessType;        // 业务类型
    
    @Column(name = "network_type")
    @Pattern(regexp = "^(4G|5G)$", message = "网络类型只能是4G或5G")
    private String networkType;         // 网络类型
    
    private LocalDate time;         // 时间（仅日期）
    
    @Column(name = "total_data_count")
    private Long totalDataCount;     // 总数据条数
    
    @Column(name = "received_count")
    private Long receivedCount;      // 接收条数
    
    @Column(name = "total_file_count")
    private Long totalFileCount;     // 总文件总数
    
    @Column(name = "received_file_count")
    private Long receivedFileCount;  // 接收文件总数

    @Column(name = "received_rate")
    private String receivedRate;  // 接收文件比率

    @Column(name = "inbound_rate")
    private String inboundRate;  // 入库文件比率

    @Column(name = "phone_null_rate")
    private String phoneNullRate;       // 用户号码空值率
    
    @Column(name = "domain_null_rate")
    private String domainNullRate;      // 域名空值率
    
    @Column(name = "dest_ip_null_rate")
    private String destIpNullRate;      // 目的ip空值率
    
    @Column(name = "dest_port_null_rate")
    private String destPortNullRate;    // 目的端口空值率
    
    @Column(name = "source_ip_null_rate")
    private String sourceIpNullRate;    // 源公网ip空值率
    
    @Column(name = "source_port_null_rate")
    private String sourcePortNullRate;  // 源端口空值率
    @Column(name= "protocol_null_rate")
    private String protocolNullRate;
}