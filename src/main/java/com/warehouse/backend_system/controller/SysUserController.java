package com.warehouse.backend_system.controller;

import com.warehouse.backend_system.entity.SysUser;
import com.warehouse.backend_system.service.SysUserService;
import com.warehouse.backend_system.util.JwtUtil;
import com.warehouse.backend_system.util.Result;
import org.springframework.web.bind.annotation.*;
import com.warehouse.backend_system.dto.PageVO;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/list")
    public Result<PageVO<SysUser>> getUserList(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        PageVO<SysUser> pageVO = sysUserService.getUserPage(username,status,pageNum,pageSize);
        return Result.success(pageVO);
    }

    @GetMapping("/info/{id}")
    public Result<SysUser> getOne(@PathVariable Long id){
        return Result.success(sysUserService.getById(id));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody SysUser sysUser){
        boolean ok = sysUserService.add(sysUser);
        return ok ? Result.success("新增成功") : Result.error("新增失败");
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody SysUser sysUser){
        boolean ok = sysUserService.update(sysUser);
        return ok ? Result.success("修改成功") : Result.error("修改失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id){
        boolean ok = sysUserService.remove(id);
        return ok ? Result.success("删除成功") : Result.error("删除失败");
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody SysUser sysUser){
        SysUser user = sysUserService.loginGetUser(sysUser.getUsername(), sysUser.getPassword());
        if(user != null){
            //生成jwt token
            String token = JwtUtil.generateToken(user.getId(),user.getUsername());
            return Result.success(token);
        }else{
            return Result.fail(500,"账号或密码错误");
        }
    }


}