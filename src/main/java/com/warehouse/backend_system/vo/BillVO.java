package com.warehouse.backend_system.vo;

import com.warehouse.backend_system.entity.MaterialBillItem;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BillVO {
    private Long id;
    private String billNo;
    private Integer billType;
    private Long operatorId;
    private String remark;
    private Integer status;
    private LocalDateTime billTime;
    private LocalDateTime createTime;
    //一张单据对应多条明细
    private List<MaterialBillItem> itemList;
}