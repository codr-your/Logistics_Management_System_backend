package com.warehouse.backend_system.mapper;

import com.warehouse.backend_system.entity.MaterialBill;
import com.warehouse.backend_system.vo.BillVO;
import com.warehouse.backend_system.vo.DashboardChartVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MaterialBillMapper {

    int insertBill(MaterialBill bill);
    //查询全部
    @Select("select * from material_bill")
    List<MaterialBill> selectAll();

    //多条件查询：单据类型、业务时间范围
    @Select("<script>" +
            "select id,bill_no,bill_type,operator_id,remark,bill_time,create_time from material_bill " +
            "<where>" +
            "   <if test='billType != null'> and bill_type = #{billType}</if>" +
            "   <if test='startTime != null'> and bill_time &gt;= #{startTime}</if>" +
            "   <if test='endTime != null'> and bill_time &lt;= #{endTime}</if>" +
            "</where>" +
            " order by bill_time desc" +
            "</script>")
    List<MaterialBill> selectList(@Param("billType") Integer billType,
                                  @Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);

    //根据id查询
    @Select("select * from material_bill where id = #{id}")
    MaterialBill selectById(@Param("id") Long id);

    //校验单据编号重复
    @Select("select * from material_bill where bill_no = #{billNo}")
    MaterialBill selectByBillNo(@Param("billNo") String billNo);

    //新增单据主表，返回主键id
    @Insert("insert into material_bill(bill_no,bill_type,operator_id,remark,bill_time,create_time) " +
            "values(#{billNo},#{billType},#{operatorId},#{remark},#{billTime},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaterialBill bill);

    //修改，create_time不参与更新
    @Update("update material_bill set bill_no=#{billNo},bill_type=#{billType},operator_id=#{operatorId},remark=#{remark} where id=#{id}")
    int update(MaterialBill bill);

    //删除
    @Delete("delete from material_bill where id = #{id}")
    int deleteById(@Param("id") Long id);

//条件查询单据列表

    List<MaterialBill> selectBillList(@Param("billNo") String billNo,
                                  @Param("billType") Integer billType);
//根据id查询物料单据详情（用于回显，包含明细）
    BillVO selectBillWithItemById(@Param("id") Long id);

    /**
     * 查询最新5条单据，按创建时间倒序
     */
    @Select("select * from material_bill order by create_time desc limit 5")
    List<MaterialBill> selectLatestBill();


    /**
     * 修正：统计今日单据数量 billType:1入库 2出库
     */
    @Select("select count(*) from material_bill where DATE(bill_time)=#{today} and bill_type=#{billType}")
    Integer countTodayBill(@Param("today") java.time.LocalDate today, @Param("billType") Integer billType);

    /**
     * 近7天，按日期统计入库、出库单据数量（业务时间 bill_time）
     */
    @Select("SELECT DATE_FORMAT(bill_time,'%Y-%m-%d') AS date, " +
            "SUM(IF(bill_type=1,1,0)) AS inCount, " +
            "SUM(IF(bill_type=2,1,0)) AS outCount " +
            "FROM material_bill " +
            "WHERE DATE(bill_time) >= DATE_SUB(CURDATE(),INTERVAL 6 DAY) " +
            "GROUP BY DATE_FORMAT(bill_time,'%Y-%m-%d') " +
            "ORDER BY DATE_FORMAT(bill_time,'%Y-%m-%d')")
    List<DashboardChartVO> selectSevenDayBillStat();


    BillVO getOutBillMonthStat();
    List<BillVO> getOutPieData();
    Integer getPendingOutCount();

    int updateBillStatus(@Param("id") Long id,
                         @Param("status") Integer status,
                         @Param("billTime") LocalDateTime billTime);

}