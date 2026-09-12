package com.warehouse.backend_system.service;
import com.warehouse.backend_system.entity.StockRecord;
import com.warehouse.backend_system.vo.DashboardChartVO;
import com.warehouse.backend_system.vo.StockRecordStatVO;
import com.warehouse.backend_system.vo.StockRecordVO;

import java.time.LocalDateTime;
import java.util.List;

public interface StockRecordService {
    List<StockRecord> getAll();
    // 多条件查询流水列表
    List<StockRecord> getList(Long materialId, LocalDateTime startTime, LocalDateTime endTime);

    // 根据id查询单条
    StockRecord getById(Long id);

    // 新增流水
    boolean add(StockRecord record);

    //查询库存流水列表（带物料名称编码，支持物料名称搜索）
    List<StockRecordVO> getRecordList(String materialName);

    //获取页面顶部统计卡片数据
    StockRecordStatVO getStockStat();

    // 获取7天图表数据，直接复用Mapper已有的selectSevenDayRecord
    List<DashboardChartVO> getSevenDayChart();

}
