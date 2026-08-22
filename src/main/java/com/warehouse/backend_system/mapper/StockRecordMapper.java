package com.warehouse.backend_system.mapper;
import com.warehouse.backend_system.entity.StockRecord;
import com.warehouse.backend_system.vo.DashboardChartVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface StockRecordMapper {
    //查询所有库存记录
    @Select("select * from stock_record")
    List<StockRecord> selectAll();

    // 多条件查询流水列表（物料id、时间范围）
    @Select("<script>" +
            "select id,material_id,bill_item_id,change_num,after_stock,operate_type,bill_no,operator_name,create_time from stock_record " +
            "<where>" +
            "   <if test='materialId != null'>" +
            "       and material_id = #{materialId}" +
            "   </if>" +
            "   <if test='startTime != null'>" +
            "       and create_time &gt;= #{startTime}" +
            "   </if>" +
            "   <if test='endTime != null'>" +
            "       and create_time &lt;= #{endTime}" +
            "   </if>" +
            "</where>" +
            " order by create_time desc" +
            "</script>")
    List<StockRecord> selectList(@Param("materialId") Long materialId,
                                 @Param("startTime") LocalDateTime startTime,
                                 @Param("endTime") LocalDateTime endTime);

    // 根据id查询单条流水
    @Select("select * from stock_record where id = #{id}")
    StockRecord selectById(@Param("id") Long id);

    // 新增流水
    // 新增
    @Insert("insert into stock_record(material_id,before_stock,after_stock,change_num,operate_type,bill_no,operator_name,create_time,bill_item_id) " +
            "values(#{materialId},#{beforeStock},#{afterStock},#{changeNum},#{operateType},#{billNo},#{operatorName},#{createTime},#{billItemId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(StockRecord record);

    /** 根据id查询物料用于获取当前库存 */
    @Select("select stock_num from material where id=#{id}")
    Integer selectStockById(Long id);

    /** 更新物料库存 */
    @Update("update material set stock_num=#{stockNum},update_time=now() where id=#{id}")
    int updateStock(@Param("id") Long id,@Param("stockNum") Integer stockNum);

    //查询最近7天的库存记录渲染图表数据
    List<DashboardChartVO> selectSevenDayRecord();

}
