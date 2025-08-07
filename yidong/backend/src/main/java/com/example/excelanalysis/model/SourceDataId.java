package com.example.excelanalysis.model;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class SourceDataId implements Serializable {
    @Column(name = "ip")
    private String ip;
    @Column(name = "business_type")
    private String businessType;        // 业务类型
    @Column(name = "time")
    private LocalDate time;             // 时间
}