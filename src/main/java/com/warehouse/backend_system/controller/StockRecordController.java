package com.warehouse.backend_system.controller;


import com.warehouse.backend_system.entity.StockRecord;
import com.warehouse.backend_system.service.StockRecordService;
import com.warehouse.backend_system.util.Result;
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
}
