package com.warehouse.backend_system.dto;

import com.warehouse.backend_system.entity.MaterialBillItem;
import lombok.Data;
import java.util.List;

@Data
public class BillSubmitDTO {
    private String billNo;
    /** 1入库 2出库，前端传字符串 */
    private String billType;
    private Long operatorId;
    private String remark;
    private List<MaterialBillItem> itemList;
}


