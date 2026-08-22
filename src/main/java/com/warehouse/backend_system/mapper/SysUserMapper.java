package com.warehouse.backend_system.mapper;
import com.warehouse.backend_system.entity.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysUserMapper {
    //查询所有用户

    List<SysUser> selectUserList(@Param("username") String username, @Param("status") Integer status);

    @Select("select * from sys_user where id = #{id}")
    SysUser selectById(Long id);

    @Insert("insert into sys_user(username,password,real_name,role,status,create_time) " +
            "values(#{username},#{password},#{realName},#{role},#{status},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SysUser sysUser);

    @Update("update sys_user set username=#{username},password=#{password},real_name=#{realName}," +
            "role=#{role},status=#{status} where id=#{id}")
    int update(SysUser sysUser);

    @Delete("delete from sys_user where id = #{id}")
    int deleteById(Long id);
    //根据用户名查询用户
    SysUser selectByUsername(@Param("username") String username);
//查询用户总数
    @Select("select count(*) from sys_user")
    Long selectUserCount();
//分页查询用户列表
    List<SysUser> selectUserPage(@Param("username")String username,
                                 @Param("status")Integer status,
                                 @Param("offset")Integer offset,
                                 @Param("pageSize")Integer pageSize);

    Long countUser(@Param("username")String username,@Param("status")Integer status);

    SysUser selectByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

}
