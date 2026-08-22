package com.warehouse.backend_system.service.impl;


import com.warehouse.backend_system.entity.StockRecord;
import com.warehouse.backend_system.mapper.StockRecordMapper;
import com.warehouse.backend_system.service.StockRecordService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockRecordServiceImpl implements StockRecordService {
    @Resource
    private StockRecordMapper stockRecordMapper;
    @Override
    public List<StockRecord> getAll() {
        return stockRecordMapper.selectAll();
    }

    @Override
    public List<StockRecord> getList(Long materialId, LocalDateTime startTime, LocalDateTime endTime) {
        return stockRecordMapper.selectList(materialId, startTime, endTime);
    }

    @Override
    public StockRecord getById(Long id) {
        return stockRecordMapper.selectById(id);
    }

    @Override
    public boolean add(StockRecord record) {
        // 后端自动填充创建时间
        record.setCreateTime(LocalDateTime.now());
        return stockRecordMapper.insert(record) > 0;
    }
}
