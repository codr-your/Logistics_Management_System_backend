package com.warehouse.backend_system.mapper;
import com.warehouse.backend_system.entity.Material;
import com.warehouse.backend_system.entity.MaterialBill;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MaterialMapper {
    @Select("select * from material")
    List<Material> selectAll();
    //根据id查询物料信息
    @Select("select * from material where id = #{id}")
    Material selectById(Long id);

    // useGeneratedKeys 获取数据库自增主键id
    @Insert("insert into material(material_code,material_name,spec,stock_num,warn_num,unit,status,create_time,update_time) " +
            "values(#{materialCode},#{materialName},#{spec},#{stockNum},#{warnNum},#{unit},#{status},#{createTime},#{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Material material);
    //根据id更新物料信息

    //根据id更新物料信息
    @Update("update material set material_code=#{materialCode},material_name=#{materialName},spec=#{spec}," +
            "stock_num=#{stockNum},warn_num=#{warnNum},unit=#{unit},status=#{status},update_time=#{updateTime} where id=#{id}")
    int update(Material material);
    //根据id删除物料
    @Delete("delete from material where id = #{id}")
    int deleteById(Long id);


    List<Material> selectByCondition(@Param("materialCode") String materialCode,
                                     @Param("materialName") String materialName,
                                     @Param("spec") String spec,
                                     @Param("status") Integer status);
    //根据物料编码查询物料信息
    @Select("select * from material where material_code = #{materialCode}")
    Material selectByCode(@Param("materialCode") String materialCode);

    /**
     * 查询库存低于预警值的物料
     */
    @Select("select * from material where stock_num < warn_num")
    List<Material> selectWarnMaterial();

    /**
     * 统计预警物料数量
     */
    @Select("select count(*) from material where stock_num < warn_num")
    Long selectWarnCount();

    /**
     * 统计物料总数量
     */
    @Select("select count(*) from material")
    Long selectMaterialCount();



}
