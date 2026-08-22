package com.warehouse.backend_system.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private Integer role;
    private Integer status;
    private LocalDateTime createTime;

}