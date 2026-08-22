package com.warehouse.backend_system.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaterialBill {
    private Long id;
    private String billNo;
    private Integer billType;
    private Long operatorId;
    private String remark;
    private LocalDateTime billTime;
    private LocalDateTime createTime;
    private Integer status;
    private LocalDateTime updateTime;
}