package com.yc.tx.vo;

import lombok.Data;

import java.io.Serializable;

@Data
//web传给控制层的
public class AccountVo implements Serializable {
    private static final long serialVersionUID = -3693150918135298227L;
    private Integer accountId;
    private Double money;
    private Integer inAccountId;
}
