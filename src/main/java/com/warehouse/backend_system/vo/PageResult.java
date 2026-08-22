package com.warehouse.backend_system.vo;

import lombok.Data;
import java.util.List;

@Data
public class PageResult<T> {
    private Long total;
    private List<T> records;
    private Integer pageNum;
    private Integer pageSize;
}