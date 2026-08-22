package com.warehouse.backend_system.controller;

import com.warehouse.backend_system.dto.DashboardStatDTO;
import com.warehouse.backend_system.entity.Material;
import com.warehouse.backend_system.entity.MaterialBill;
import com.warehouse.backend_system.service.DashboardService;
import com.warehouse.backend_system.util.Result;
import com.warehouse.backend_system.vo.DashboardChartVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    @GetMapping("/getStat")
    public Result<DashboardStatDTO> getStat(){
        DashboardStatDTO stat = dashboardService.getDashboardStat();
        return Result.success(stat);
    }


    /**
     * 近7天出入库趋势图表数据
     */
    @GetMapping("/getStatChart")
    public Result<List<DashboardChartVO>> getStatChart(){
        List<DashboardChartVO> list = dashboardService.getSevenDayChart();
        return Result.success(list);
    }

    /**
     * 库存预警物料列表
     */
    @GetMapping("/warnMaterial")
    public Result<List<Material>> warnMaterial(){
        List<Material> list = dashboardService.getWarnMaterialList();
        return Result.success(list);
    }

    /**
     * 最近出入库单据（5条）
     */
    @GetMapping("/latestBill")
    public Result<List<MaterialBill>> latestBill(){
        List<MaterialBill> list = dashboardService.getLatestBill();
        return Result.success(list);
    }

}