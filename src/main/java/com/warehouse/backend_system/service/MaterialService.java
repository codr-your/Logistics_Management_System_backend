package com.warehouse.backend_system.service;
import com.warehouse.backend_system.entity.Material;
import com.warehouse.backend_system.entity.MaterialBill;
import com.warehouse.backend_system.vo.PageResult;

import java.util.List;
public interface MaterialService {
    List<Material> getAll();

   //定义对应的方法，用于操作数据库中的material表
    //根据id查询物料信息
    Material getById(Long id);
    //添加物料
    boolean add(Material material);
    //修改物料
    boolean update(Material material);
    //删除物料
    boolean remove(Long id);
    // 新增多条件查询方法
    List<Material> getListByCondition(String materialCode, String materialName, String spec, Integer status);

 //查询库存预警物料
 List<Material> getWarnList();

 //获取物料总数量
 Long getMaterialTotalCount();
 //获取预警物料数量
 Long getWarnMaterialCount();

 //分页条件查询
 PageResult<Material> pageQuery(
         Integer pageNum,
         Integer pageSize,
         String materialCode,
         String materialName,
         String spec,
         Integer status
 );




}
