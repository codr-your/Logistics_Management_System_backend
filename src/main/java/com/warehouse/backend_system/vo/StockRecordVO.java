package com.warehouse.backend_system.vo;
//作用，联表查询，返回表格需要的物料编码，物料名称，stock_record表没有这两个字段，需要VO承载连表结果
import lombok.Data;

import java.time.LocalDateTime;


//添加@Data注解，自动生成getter和setter方法
@Data
public class StockRecordVO {
    private Long id;
    private String materialCode;//物料编码
    private String materialName;//物料名称
    private String operateType;//操作类型
    private Integer changeNum;//变更数量
    private Integer afterStock;//变更后库存数量
    private LocalDateTime createTime;//创建时间

}
