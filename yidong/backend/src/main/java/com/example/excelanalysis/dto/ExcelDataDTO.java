package com.example.excelanalysis.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ExcelDataDTO {
    private String businessType;
    private LocalDate time;
    private Long totalDataCount;
    private Long totalFileCount;
}