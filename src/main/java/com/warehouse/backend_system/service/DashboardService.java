package com.warehouse.backend_system.service;

import com.warehouse.backend_system.dto.DashboardStatDTO;

import com.warehouse.backend_system.vo.DashboardChartVO;
import com.warehouse.backend_system.vo.DashboardStatVO;
import com.warehouse.backend_system.entity.Material;
import com.warehouse.backend_system.entity.MaterialBill;
import java.util.List;


public interface DashboardService {
    //获取顶部4个统计卡片
    DashboardStatDTO getDashboardStat();
    //获取近7天出入库统计图表
    List<DashboardChartVO> getSevenDayChart();
    //库存预警物料列表
    List<Material> getWarnMaterialList();
    //获取最新单据列表（取5条）
    List<MaterialBill> getLatestBill();

}