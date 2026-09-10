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

    //本月出库统计
    BillVO getOutBillMonthStat();
    //出库饼图统计
    List<BillVO> getOutPieData();
    //待确认出库草稿数量
    Integer getPendingOutCount();


    /**
     * 保存草稿单据（入库/出库草稿，status=0；只存主表+明细，不修改库存、不生成库存流水）
     * @param billNo 单据编号
     * @param billType "1"入库 / "2"出库 字符串
     * @param operatorId 操作人id
     * @param remark 备注
     * @param itemList 明细列表
     * @return 成功true
     */
    boolean saveDraftBill(String billNo, String billType, Long operatorId, String remark, List<MaterialBillItem> itemList);


    /**
     * 草稿单据提交（出库草稿转正，扣库存）
     * @param billId 单据id
     * @return boolean
     */
    boolean submitDraftBill(Long billId);


}
