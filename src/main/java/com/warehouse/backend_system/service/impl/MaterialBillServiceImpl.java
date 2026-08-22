package com.warehouse.backend_system.service.impl;

import com.warehouse.backend_system.entity.Material;
import com.warehouse.backend_system.entity.MaterialBillItem;
import com.warehouse.backend_system.entity.StockRecord;
import com.warehouse.backend_system.mapper.MaterialBillItemMapper;
import com.warehouse.backend_system.mapper.MaterialBillMapper;
import com.warehouse.backend_system.entity.MaterialBill;
import com.warehouse.backend_system.mapper.MaterialMapper;
import com.warehouse.backend_system.mapper.StockRecordMapper;
import com.warehouse.backend_system.service.MaterialBillService;
import com.warehouse.backend_system.vo.BillVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;

import com.warehouse.backend_system.entity.SysUser;
import com.warehouse.backend_system.mapper.SysUserMapper;

@Service
public class MaterialBillServiceImpl implements MaterialBillService {
        @Resource
        private MaterialBillMapper materialBillMapper;

        @Resource
        private MaterialBillItemMapper materialBillItemMapper;
        @Resource
        private MaterialMapper materialMapper;
        @Resource
        private StockRecordMapper stockRecordMapper;
        @Resource
        private SysUserMapper sysUserMapper;

        @Override
        public List<MaterialBill> getAll() {
                return materialBillMapper.selectAll();
        }

        @Override
        public List<MaterialBill> getList(Integer billType, LocalDateTime startTime, LocalDateTime endTime) {
                return materialBillMapper.selectList(billType, startTime, endTime);
        }

        @Override
        public MaterialBill getById(Long id) {
                return materialBillMapper.selectById(id);
        }

        @Override
        public boolean add(MaterialBill bill) {
                MaterialBill existBill = materialBillMapper.selectByBillNo(bill.getBillNo());
                if(existBill != null){
                        return false;
                }
                bill.setCreateTime(LocalDateTime.now());
                if(bill.getBillTime() == null){
                        bill.setBillTime(LocalDateTime.now());
                }
                bill.setStatus(0); //草稿状态
                return materialBillMapper.insert(bill) > 0;
        }

        @Override
        public boolean update(MaterialBill bill) {
                MaterialBill oldBill = materialBillMapper.selectById(bill.getId());
                if(oldBill == null){
                        return false;
                }
                if (oldBill.getStatus() == 1){
                        throw new RuntimeException("已提交的单据不允许修改！");
                }
                bill.setUpdateTime(LocalDateTime.now());
                return materialBillMapper.update(bill) > 0;
        }

        @Override
        public boolean remove(Long id) {
                MaterialBill oldBill = materialBillMapper.selectById(id);
                if(oldBill == null){
                        return false;
                }
                if(oldBill.getStatus() ==1){
                        throw new RuntimeException("已提交的单据不允许删除！");
                }
                return materialBillMapper.deleteById(id) > 0;
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public boolean submitBill(String billNo, String billType, Long operatorId, String remark, List<MaterialBillItem> itemList) {
                //1.校验操作人用户是否存在
                SysUser operator = sysUserMapper.selectById(operatorId);
                if(operator == null){
                        throw new RuntimeException("操作用户不存在");
                }
                String operatorName = operator.getRealName();

                //2.校验单据编号不能重复
                MaterialBill existBill = materialBillMapper.selectByBillNo(billNo);
                if(existBill != null){
                        throw new RuntimeException("单据编号已经存在");
                }

                //3.保存单据主表
                MaterialBill bill = new MaterialBill();
                bill.setBillNo(billNo);
                bill.setBillType(Integer.parseInt(billType));
                bill.setOperatorId(operatorId);
                bill.setRemark(remark);
                bill.setBillTime(LocalDateTime.now());
                bill.setCreateTime(LocalDateTime.now());
                bill.setStatus(1); //标记已提交
                int insertCnt = materialBillMapper.insertBill(bill);
                if(insertCnt <= 0){
                        throw new RuntimeException("单据主表保存失败");
                }
                Long billId = bill.getId();

                //4.逐条插入明细
                for(MaterialBillItem item : itemList){
                        item.setBillId(billId);
                        int rows = materialBillItemMapper.insert(item);
                        if(rows <= 0){
                                throw new RuntimeException("单据明细插入数据库失败");
                        }
                        if(item.getId() == null){
                                throw new RuntimeException("明细保存失败，无法获取明细主键id");
                        }
                }
                //5.循环处理每一条物料，更新物料库存、生成流水
                for(MaterialBillItem item : itemList){
                        Long materialId = item.getMaterialId();
                        Integer operateNum = item.getOperateNum();

                        Material material = materialMapper.selectById(materialId);
                        if(material == null){
                                throw new RuntimeException("物料id:"+materialId+"不存在");
                        }

                        Integer beforeStock = material.getStockNum();
                        Integer afterStock;
                        String operateTypeText;

                        if("1".equals(billType)){
                                //入库
                                afterStock = beforeStock + operateNum;
                                operateTypeText = "入库";
                        }else if("2".equals(billType)){
                                //出库，库存校验
                                if(beforeStock < operateNum){
                                        throw new RuntimeException("出库失败！当前库存："+beforeStock+"，申请出库："+operateNum+"，库存不足");
                                }
                                afterStock = beforeStock - operateNum;
                                operateTypeText = "出库";
                        }else{
                                throw new RuntimeException("单据类型参数错误，只能传字符串\"1\"入库，\"2\"出库");
                        }

                        //更新物料库存
                        material.setStockNum(afterStock);
                        materialMapper.update(material);

                        //库存流水记录
                        StockRecord record = new StockRecord();
                        record.setMaterialId(materialId);
                        record.setBeforeStock(beforeStock);
                        record.setAfterStock(afterStock);
                        record.setOperateType(operateTypeText);
                        record.setBillNo(billNo);
                        record.setOperatorName(operatorName);
                        record.setChangeNum("1".equals(billType) ? operateNum : -operateNum);
                        record.setCreateTime(LocalDateTime.now());
                        record.setBillItemId(item.getId());

                        stockRecordMapper.insert(record);
                }
                return true;
        }

        @Override
        public List<MaterialBill> getBillList(String billNo, Integer billType) {
                return materialBillMapper.selectBillList(billNo, billType);
        }

        @Override
        public BillVO getBillDetail(Long id) {
                return materialBillMapper.selectBillWithItemById(id);
        }

        @Override
        public List<MaterialBill> getLatestBill() {
                return materialBillMapper.selectLatestBill();
        }
}