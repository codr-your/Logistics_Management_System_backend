package com.warehouse.backend_system.service.impl;

import com.warehouse.backend_system.entity.Material;
import com.warehouse.backend_system.entity.MaterialBill;
import com.warehouse.backend_system.entity.MaterialBillItem;
import com.warehouse.backend_system.mapper.MaterialMapper;
import com.warehouse.backend_system.service.MaterialBillItemService;
import org.springframework.stereotype.Service;
import com.warehouse.backend_system.mapper.MaterialBillItemMapper;
import javax.annotation.Resource;
import java.util.List;
import com.warehouse.backend_system.mapper.MaterialBillMapper;
@Service
public class MaterialBillItemServiceImpl implements MaterialBillItemService {
    @Resource
        private MaterialBillItemMapper materialBillItemMapper;
    @Resource
    private MaterialMapper materialMapper;
    @Resource
    private MaterialBillMapper materialBillMapper;
    @Override
    public List<MaterialBillItem> getAll() {
        return materialBillItemMapper.selectAll();
    }

    @Override
    public MaterialBillItem getById(Long id) {
        return materialBillItemMapper.selectById(id);
    }

    @Override
    public boolean add(MaterialBillItem item) {
        // 校验1：单据id必须存在
        MaterialBill bill = materialBillMapper.selectById(item.getBillId());
        if(bill == null){
            return false;
        }
        // 校验2：物料id必须存在
        Material material = materialMapper.selectById(item.getMaterialId());
        if(material == null){
            return false;
        }
        // 校验3：出入库数量必须大于0
        if(item.getOperateNum() == null || item.getOperateNum() <= 0){
            return false;
        }
        return materialBillMapper.insertBill(bill) > 0;
    }

    @Override
    public boolean update(MaterialBillItem item) {
        // 校验1：明细id必须存在
        MaterialBillItem oldItem = materialBillItemMapper.selectById(item.getId());
        if(oldItem == null){
            return false;
        }
        // 校验2：单据id必须存在
        MaterialBill bill = materialBillMapper.selectById(item.getBillId());
        if(bill == null){
            return false;
        }
        // 校验3：物料id必须存在
        Material material = materialMapper.selectById(item.getMaterialId());
        if(material == null){
            return false;
        }
        // 校验4：出入库数量必须大于0
        if(item.getOperateNum() == null || item.getOperateNum() <= 0){
            return false;
        }
        return materialBillItemMapper.update(item) > 0;
    }

    @Override
    public boolean remove(Long id) {
        // 校验：id必须存在
        MaterialBillItem item = materialBillItemMapper.selectById(id);
        if(item == null){
            return false;
        }
        return materialBillItemMapper.deleteById(id) > 0;
    }


}
