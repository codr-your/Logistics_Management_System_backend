package com.warehouse.backend_system.service.impl;


import com.warehouse.backend_system.entity.StockRecord;
import com.warehouse.backend_system.mapper.StockRecordMapper;
import com.warehouse.backend_system.service.StockRecordService;
import com.warehouse.backend_system.vo.DashboardChartVO;
import com.warehouse.backend_system.vo.StockRecordStatVO;
import com.warehouse.backend_system.vo.StockRecordVO;
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


    //查询库存流水列表（带物料名称编码，支持物料名称搜索）用于库存记录页面展示
    @Override
    public List<StockRecordVO> getRecordList(String materialName) {
        return stockRecordMapper.selectRecordWithMaterial(materialName);
    }

    @Override
    public StockRecordStatVO getStockStat() {
        return stockRecordMapper.selectStockRecordStat();
    }

    @Override
    public List<DashboardChartVO> getSevenDayChart() {
        // 直接调用你已经写好的 selectSevenDayRecord，复用原有SQL，不重复写
        return stockRecordMapper.selectSevenDayRecord();
    }





}
