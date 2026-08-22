package com.warehouse.backend_system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warehouse.backend_system.entity.Material;
import com.warehouse.backend_system.entity.MaterialBill;
import com.warehouse.backend_system.mapper.MaterialMapper;
import com.warehouse.backend_system.service.MaterialService;
import com.warehouse.backend_system.vo.PageResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Resource
    private MaterialMapper materialMapper;
    @Override
    public List<Material> getAll() {
        return materialMapper.selectAll();
    }
//通过id查询物料信息
    @Override
    public Material getById(Long id) {
        return materialMapper.selectById(id);
    }
//添加物料
@Override
public boolean add(Material material) {
    //后端填充时间，前端不用传
    // 检查物料编码是否存在
    Material existingMaterial = materialMapper.selectByCode(material.getMaterialCode());
    if (existingMaterial != null) {
        return false;
    }
    // 新增物料，初始库存设置为0，解决stock_num不能为null
    if(material.getStockNum() == null){
        material.setStockNum(0);
    }
    //预警数量也可以给默认值
    if(material.getWarnNum() == null){
        material.setWarnNum(0);
    }

    material.setCreateTime(LocalDateTime.now());
    material.setUpdateTime(LocalDateTime.now());
    return materialMapper.insert(material) > 0;
}
//更新物料信息（进行编码冲突检查）
    @Override
    public boolean update(Material material) {
        // 根据编码查询数据库中的物料
        Material existMaterial = materialMapper.selectByCode(material.getMaterialCode());

        // 如果编码存在，并且不是当前这条数据，说明和别的物料编码冲突
        if(existMaterial != null && !existMaterial.getId().equals(material.getId())){
            return false;
        }

        // 修改只更新updateTime
        material.setUpdateTime(LocalDateTime.now());
        return materialMapper.update(material) > 0;
    }
    @Override
    public boolean remove(Long id) {
        return materialMapper.deleteById(id) > 0;
    }

    @Override
    public List<Material> getListByCondition(String materialCode, String materialName, String spec, Integer status) {
        return materialMapper.selectByCondition(materialCode,materialName,spec,status);
    }
//库存预警物料查询
    @Override
    public List<Material> getWarnList() {
        return materialMapper.selectWarnMaterial();
    }
//获取物料总数量
    @Override
    public Long getMaterialTotalCount() {
        return materialMapper.selectMaterialCount();
    }
//预警物料数量
    @Override
    public Long getWarnMaterialCount() {
        return materialMapper.selectWarnCount();
    }

    //分页实现
    @Override
    public PageResult<Material> pageQuery(Integer pageNum, Integer pageSize, String materialCode, String materialName, String spec, Integer status) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<Material> list = materialMapper.selectByCondition(materialCode, materialName, spec, status);
        PageInfo<Material> pageInfo = new PageInfo<>(list);

        PageResult<Material> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRecords(pageInfo.getList());
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        return pageResult;
    }


}
