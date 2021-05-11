package com.yc.webress.Vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//排除json中空的属性
//这是控制层返回给web界面的结果
public class JsonModel<T> implements Serializable {

    private static final long serialVersionUID = -6998078720285568955L;
    private Integer code;
    private Object obj;
    private String msg;
    private String url;
}
