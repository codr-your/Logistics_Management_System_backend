package com.warehouse.backend_system.controller;

import com.warehouse.backend_system.entity.MaterialBillItem;
import com.warehouse.backend_system.util.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;
import com.warehouse.backend_system.service.MaterialBillItemService;

@RestController
@RequestMapping("/materialBillItem")
public class MaterialBillItemController {
  @Resource
  private MaterialBillItemService materialBillItemService;
  @RequestMapping("/listAll")
  public Result<List<MaterialBillItem>> getMaterialBillItemList(){
    List<MaterialBillItem> list = materialBillItemService.getAll();
    return Result.success(list);
  }

  // 2.根据id查询单条明细
  @GetMapping("/{id}")
  public Result<MaterialBillItem> getById(@PathVariable Long id){
    MaterialBillItem item = materialBillItemService.getById(id);
    if(item == null){
      return Result.fail(500,"明细不存在");
    }
    return Result.success(item);
  }

  // 3.新增明细
  @PostMapping("/add")
  public Result<String> add(@RequestBody MaterialBillItem item){
    boolean ok = materialBillItemService.add(item);
    if(ok){
      return Result.success("新增明细成功");
    }else{
      return Result.fail(500,"新增失败，单据/物料不存在或数量不合法");
    }
  }

  // 4.修改明细
  @PutMapping("/update")
  public Result<String> update(@RequestBody MaterialBillItem item){
    boolean ok = materialBillItemService.update(item);
    if(ok){
      return Result.success("修改明细成功");
    }else{
      return Result.fail(500,"修改失败，明细/单据/物料不存在或数量不合法");
    }
  }

  // 5.删除明细
  @DeleteMapping("/delete/{id}")
  public Result<String> delete(@PathVariable Long id){
    boolean ok = materialBillItemService.remove(id);
    if(ok){
      return Result.success("删除明细成功");
    }else{
      return Result.fail(500,"删除失败，明细不存在");
    }
  }
}
