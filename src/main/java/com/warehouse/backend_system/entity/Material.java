package com.warehouse.backend_system.entity;
//全部使用 @Data lombok 注解，数据库下划线自动转驼峰，对应之前的 5 张数据表。
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Material {
    private Long id;
    private String materialCode;
    private String materialName;
    private String spec;
    private Integer stockNum;
    private Integer warnNum;
    private String unit;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}