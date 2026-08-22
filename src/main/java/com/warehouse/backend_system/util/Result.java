package com.warehouse.backend_system.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(){
        return new Result<>(200,"操作成功",null);
    }
    public static <T> Result<T> success(T data){
        return new Result<>(200,"操作成功",data);
    }
    public static <T> Result<T> fail(Integer code,String msg){
        return new Result<>(code,msg,null);
    }

    public static Result<String> error(String msg) {
        return new Result<>(500,msg,null);
    }

    public static Result<String> successMsg(String msg){
        return new Result<>(200,msg,null);
    }
}