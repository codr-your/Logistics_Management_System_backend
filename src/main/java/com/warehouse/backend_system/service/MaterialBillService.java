package com.warehouse.backend_system.service;

import com.warehouse.backend_system.entity.MaterialBillItem;
import com.warehouse.backend_system.vo.BillVO;
import com.warehouse.backend_system.entity.MaterialBill;

import java.time.LocalDateTime;
import java.util.List;


public interface MaterialBillService {

    List<MaterialBill> getAll();
    List<MaterialBill> getList(Integer billType, LocalDateTime startTime, LocalDateTime endTime);
    MaterialBill getById(Long id);
    boolean add(MaterialBill bill);
    boolean update(MaterialBill bill);
    boolean remove(Long id);
    /**
     * 提交单据（入库/出库统一入口）
     * @param billNo 单据编号
     * @param billType 单据类型：入库 / 出库
     * @param operatorId 操作人ID
     * @param remark 备注
     * @param itemList 单据明细集合
     * @return true成功 false失败
     */
//提交出入库单据
    boolean submitBill(String billNo,String billType,Long operatorId,String remark,List<MaterialBillItem> itemList);


    //条件查询单据列表
    List<MaterialBill> getBillList (String billNo,Integer billType);
    //根据id查询物料单据详情（用于回显，包含明细）

    BillVO getBillDetail(Long id);


    List<MaterialBill> getLatestBill();




}
