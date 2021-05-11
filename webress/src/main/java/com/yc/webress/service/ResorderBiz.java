package com.yc.webress.service;

import com.yc.webress.Vo.CarItem;
import com.yc.webress.bean.Resorder;

import java.util.Map;

public interface ResorderBiz {
    //下订                           订单信息                  购物车信息
    public Integer comleteOrder(Resorder resorder, Map<Integer, CarItem> shopCart);
}
