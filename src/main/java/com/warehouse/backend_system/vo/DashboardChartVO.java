package com.warehouse.backend_system.vo;

import lombok.Data;

@Data
public class DashboardChartVO {
    private String date;
    private Integer inCount;
    private Integer outCount;
}