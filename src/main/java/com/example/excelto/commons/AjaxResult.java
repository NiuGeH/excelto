package com.example.excelto.commons;

import lombok.Data;

@Data
public class AjaxResult<T> {
    private boolean success;
    private String message;
    private Integer code;
    private T data;
}