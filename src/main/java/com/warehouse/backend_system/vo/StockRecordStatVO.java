package com.warehouse.backend_system.vo;
//承载页面顶部四张卡片的返回数据
import lombok.Data;
@Data
public class StockRecordStatVO {
    private Long totalRecord;//总变动记录数
    private Integer totalIn;//累计入库
    private Integer totalOut;//累计出库
    private Long materialTypeCnt;//当前物料总类

}
