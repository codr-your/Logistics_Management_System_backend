package com.warehouse.backend_system.controller;
import com.warehouse.backend_system.entity.Material;
import com.warehouse.backend_system.service.MaterialService;
import com.warehouse.backend_system.util.Result;
import com.warehouse.backend_system.vo.PageResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/material")
public class MaterialController {
    @Resource
    private MaterialService materialService;
    //查询所有物料
    @GetMapping("/listAll")
public Result<List<Material>> getAll(){
    List<Material> list = materialService.getAll();
    return Result.success(list);
   }
    //根据id查询物料信息
    @GetMapping("/info/{id}")
    public Result<Material> getOne(@PathVariable Long id){
        return Result.success(materialService.getById(id));
    }
    //添加物料
    @PostMapping("/add")
    public Result<String> add(@RequestBody Material material){
        boolean ok = materialService.add(material);
        if (ok) {
            return Result.success("新增成功");
        } else {
            return Result.fail(500,"物料编码已存在，新增失败");
        }
    }
    //修改物料
    @PutMapping("/update")
    public Result<String> update(@RequestBody Material material){
        boolean ok = materialService.update(material);
        if(ok){
            return Result.success("修改成功");
        }else{
            return Result.fail(500,"物料编码已被其他物料占用，修改失败");
        }
    }
    //删除物料
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id){
        boolean ok = materialService.remove(id);
        return ok ? Result.success("删除成功") : Result.error("删除失败");
    }
    //根据条件查询物料列表
    @GetMapping("/list")
    public Result<List<Material>> getMaterialList(
            @RequestParam(required = false) String materialCode,
            @RequestParam(required = false) String materialName,
            @RequestParam(required = false) String spec,
            @RequestParam(required = false) Integer status

    ){

        List<Material> list = materialService.getListByCondition(materialCode,materialName,spec,status);
        return Result.success(list);
    }

    @GetMapping("/warnList")
    public Result<List<Material>> warnList(){
        List<Material> warnList = materialService.getWarnList();
        return Result.success(warnList);
    }

    /**
     * 获取物料统计数据：总种类、预警数量
     */
    @GetMapping("/statistics")
    public Result<java.util.Map<String,Object>> statistics(){
        java.util.Map<String,Object> map = new java.util.HashMap<>();
        map.put("materialTotal",materialService.getMaterialTotalCount());
        map.put("warnCount",materialService.getWarnMaterialCount());
        return Result.success(map);
    }

    /**
     * 获取图表数据：返回物料名称、库存，用于柱状图
     */
    @GetMapping("/chartData")
    public Result<List<Material>> chartData(){
        List<Material> list = materialService.getAll();
        return Result.success(list);
    }

    /**
     * 分页条件查询物料【新分页接口】
     */
    @GetMapping("/page")
    public Result<PageResult<Material>> pageList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String materialCode,
            @RequestParam(required = false) String materialName,
            @RequestParam(required = false) String spec,
            @RequestParam(required = false) Integer status
    ){
        PageResult<Material> page = materialService.pageQuery(pageNum,pageSize,materialCode,materialName,spec,status);
        return Result.success(page);
    }


}
