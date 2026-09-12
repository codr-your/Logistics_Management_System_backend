package com.warehouse.backend_system.controller;


import com.warehouse.backend_system.entity.StockRecord;
import com.warehouse.backend_system.service.StockRecordService;
import com.warehouse.backend_system.util.Result;
import com.warehouse.backend_system.vo.DashboardChartVO;
import com.warehouse.backend_system.vo.StockRecordStatVO;
import com.warehouse.backend_system.vo.StockRecordVO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/stockRecord")
public class StockRecordController {
    @Resource
    private StockRecordService stockRecordService;

    @RequestMapping("/listAll")
    public Result<List<StockRecord>> getStockRecords() {
        List<StockRecord> list = stockRecordService.getAll();
        return Result.success(list);
    }

    // 1.流水列表多条件查询
    @GetMapping("/list")
    public Result<List<StockRecord>> list(
            @RequestParam(required = false) Long materialId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime
    ){
        List<StockRecord> list = stockRecordService.getList(materialId, startTime, endTime);
        return Result.success(list);
    }

    // 2.根据id查询单条流水
    @GetMapping("/{id}")
    public Result<StockRecord> getById(@PathVariable Long id){
        StockRecord record = stockRecordService.getById(id);
        if(record == null){
            return Result.fail(500,"流水记录不存在");
        }
        return Result.success(record);
    }

    // 3.新增流水（正常业务由出入库接口自动调用，这里提供手动测试入口）
    @PostMapping("/add")
    public Result<String> add(@RequestBody StockRecord record){
        boolean ok = stockRecordService.add(record);
        if(ok){
            return Result.success("新增流水成功");
        }else{
            return Result.fail(500,"新增流水失败");
        }
    }



    // ====================== 【下面是本次新增接口，追加在文件末尾，原有代码不动】 ======================
    /**
     * 库存流水列表【联表带物料编码、物料名称，支持物料名称模糊搜索】给库存记录页面表格使用
     */
    @GetMapping("/listWithMaterial")
    public Result<List<StockRecordVO>> listWithMaterial(@RequestParam(required = false) String materialName){
        List<StockRecordVO> list = stockRecordService.getRecordList(materialName);
        return Result.success(list);
    }

    /**
     * 库存记录页面顶部4张统计卡片数据
     */
    @GetMapping("/stat")
    public Result<StockRecordStatVO> getStockStat(){
        StockRecordStatVO statVO = stockRecordService.getStockStat();
        return Result.success(statVO);
    }

    /**
     * 近7天出入库图表统计，复用原有mapper的selectSevenDayRecord
     */
    @GetMapping("/sevenDayChart")
    public Result<List<DashboardChartVO>> sevenDayChart(){
        List<DashboardChartVO> list = stockRecordService.getSevenDayChart();
        return Result.success(list);
    }


}
