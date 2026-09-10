package com.warehouse.backend_system.mapper;

import com.warehouse.backend_system.entity.MaterialBillItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MaterialBillItemMapper {
        // 查询所有明细
        @Select("select * from material_bill_item")
        List<MaterialBillItem> selectAll();


        // 根据id查询单条明细
        @Select("select * from material_bill_item where id = #{id}")
        MaterialBillItem selectById(@Param("id") Long id);

        // 新增明细

        int insert(MaterialBillItem item);

        // 修改明细
        @Update("update material_bill_item set bill_id=#{billId},material_id=#{materialId},operate_num=#{operateNum} where id=#{id}")
        int update(MaterialBillItem item);

        // 删除明细
        @Delete("delete from material_bill_item where id = #{id}")
        int deleteById(@Param("id") Long id);

        // 根据单据id删除该单据下所有明细（删除单据时级联删除用）
        @Delete("delete from material_bill_item where bill_id = #{billId}")
        int deleteByBillId(@Param("billId") Long billId);

        //批量插入单据明细
        @Insert("<script>" +
                "insert into material_bill_item(bill_id,material_id,operate_num) values " +
                "<foreach collection='list' item='item' separator=','> " +
                "(#{item.billId},#{item.materialId},#{item.operateNum}) " +
                "</foreach>" +
                "</script>")
        int batchInsert(@Param("list") List<MaterialBillItem> itemList);

//根据单据id,查询单据下所有明细【新增】
        @Select("select * from material_bill_item where bill_id = #{billId}")
        List<MaterialBillItem> selectByBillId(@Param("billId") Long billId);



}
