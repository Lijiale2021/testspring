package com.yc.tx.vo;

import lombok.Data;

import java.io.Serializable;

@Data
//这是控制层返回给web界面的结果
public class ResultVo<T> implements Serializable {
    private static final long serialVersionUID = -4007332046157019135L;
    private Integer code;
    private T data;
    private String msg;
}
