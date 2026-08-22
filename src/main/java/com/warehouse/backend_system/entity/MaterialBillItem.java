package com.warehouse.backend_system.entity;

import lombok.Data;

@Data
public class MaterialBillItem {
    private Long id;
    private Long billId;
    private Long materialId;
    private Integer operateNum;
}