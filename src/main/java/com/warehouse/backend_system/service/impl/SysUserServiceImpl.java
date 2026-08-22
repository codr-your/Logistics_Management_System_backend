package com.warehouse.backend_system.service.impl;
import com.warehouse.backend_system.entity.SysUser;
import com.warehouse.backend_system.mapper.SysUserMapper;
import com.warehouse.backend_system.service.SysUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import com.warehouse.backend_system.dto.PageVO;


@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getUserList(String username, Integer status) {
        return sysUserMapper.selectUserList(username,status);
    }

    @Override
    public SysUser getById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public boolean add(SysUser sysUser) {
        sysUser.setCreateTime(LocalDateTime.now());
        return sysUserMapper.insert(sysUser) > 0;
    }

    @Override
    public boolean update(SysUser sysUser) {
        return sysUserMapper.update(sysUser) > 0;
    }

    @Override
    public boolean remove(Long id) {
        return sysUserMapper.deleteById(id) > 0;
    }

    @Override
    public boolean login(String username, String password) {
        SysUser user = loginGetUser(username, password);
        return user != null;
    }

    // ✅新增：登录查询返回完整用户对象，方案B需要
    @Override
    public SysUser loginGetUser(String username, String password) {
        SysUser user = sysUserMapper.selectByUsername(username);
        if(user == null){
            return null;
        }
        //明文密码比对
        if(user.getPassword().equals(password)){
            return user;
        }
        return null;
    }

    //分页查询用户列表
    @Override
    public PageVO<SysUser> getUserPage(String username, Integer status, Integer pageNum, Integer pageSize) {
        PageVO<SysUser> vo = new PageVO<>();
        int offset = (pageNum - 1) * pageSize;
        List<SysUser> records = sysUserMapper.selectUserPage(username,status,offset,pageSize);
        Long total = sysUserMapper.countUser(username,status);

        vo.setRecords(records);
        vo.setTotal(total);
        vo.setPageNum(pageNum);
        vo.setPageSize(pageSize);
        return vo;
    }

}