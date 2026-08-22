package com.warehouse.backend_system.dto;

import lombok.Data;

@Data
public class DashboardStatDTO {
    private Long userTotal;
    private Long materialTotal;
    private Integer todayIn;
    private Integer todayOut;
}