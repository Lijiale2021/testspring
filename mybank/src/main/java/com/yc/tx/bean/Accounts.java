package com.yc.tx.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Accounts {
    private Integer accountId;
    private Double balance;


}
