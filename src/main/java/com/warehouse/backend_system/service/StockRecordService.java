package com.warehouse.backend_system.service;
import com.warehouse.backend_system.entity.StockRecord;

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
}
