package com.warehouse.backend_system.service;

import com.warehouse.backend_system.dto.PageVO;
import com.warehouse.backend_system.entity.SysUser;
import java.util.List;

public interface SysUserService {
    List<SysUser> getUserList(String username,Integer status);
    SysUser getById(Long id);
    boolean add(SysUser sysUser);
    boolean update(SysUser sysUser);
    boolean remove(Long id);
    //登录返回true=登录成功 false=失败
    boolean login(String username,String password);

    PageVO<SysUser> getUserPage(String username, Integer status, Integer pageNum, Integer pageSize);

    SysUser loginGetUser(String username, String password);
}