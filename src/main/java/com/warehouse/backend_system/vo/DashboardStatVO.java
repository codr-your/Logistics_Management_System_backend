package com.warehouse.backend_system.vo;

import lombok.Data;

@Data
public class DashboardStatVO {
    /** 用户总数 */
    private Long userTotal;
    /** 物料总种类 */
    private Long materialTotal;
    /** 今日入库单数量 billType=1 */
    private Integer todayInBill;
    /** 今日出库单数量 billType=2 */
    private Integer todayOutBill;
}