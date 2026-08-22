package com.warehouse.backend_system.service;

import com.warehouse.backend_system.entity.MaterialBillItem;

import java.util.List;

public interface MaterialBillItemService {
    //查询所有物料账单详情
    List<MaterialBillItem> getAll();

    // 根据id查询单条
    MaterialBillItem getById(Long id);

    // 新增明细
    boolean add(MaterialBillItem item);

    // 修改明细
    boolean update(MaterialBillItem item);

    // 删除明细
    boolean remove(Long id);
}
