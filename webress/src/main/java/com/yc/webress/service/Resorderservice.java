package com.yc.webress.service;

import com.yc.webress.bean.Resorder;

import java.math.BigDecimal;
import java.util.List;

public interface Resorderservice {
    Integer neworders(Resorder resorder, Integer fid, BigDecimal dealprice, Integer num);

    Integer Cancelorders(Resorder resorder);

    List<Resorder> findAllById(Resorder resorder);
}
