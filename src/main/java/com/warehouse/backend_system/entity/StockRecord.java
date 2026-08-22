package com.warehouse.backend_system.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StockRecord {
    private Long id;
    private Long materialId;
    private Long billItemId;
    private Integer changeNum;
    private Integer afterStock;
    private LocalDateTime createTime;
    private Integer beforeStock;
    private Integer operateNum;

    private String operateType;
    private String billNo;
    private String operatorName;

}