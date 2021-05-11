package com.yc.webress.service.impl;

import com.yc.webress.Vo.CarItem;
import com.yc.webress.bean.Resorder;
import com.yc.webress.bean.Resorderitem;
import com.yc.webress.dao.ResorderDao;
import com.yc.webress.dao.ResorderitemDao;
import com.yc.webress.enums.OrderStatusEnum;
import com.yc.webress.service.ResorderBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Map;

@Service
@Transactional
public class ResorderBizImpl implements ResorderBiz {
    @Autowired
    private ResorderDao resorderDao;
    @Autowired
    private ResorderitemDao resorderitemDao;

    //下订                           订单信息                  购物车信息
    @Override
    public Integer comleteOrder(Resorder resorder, Map<Integer, CarItem> shopCart) {
        Resorder resorders = resorderDao.save(resorder);
        resorders.setOrdertime(new Timestamp(System.currentTimeMillis()));
        resorders.setDeliverytime(resorder.getDeliverTime());
        resorders.setStatus(OrderStatusEnum.NEW.getCode());
        for (Map.Entry<Integer, CarItem> entry : shopCart.entrySet()) {
            Resorderitem resorderitem = new Resorderitem();
            resorderitem.setRoid(resorder.getRoid());
            resorderitem.setDealprice(entry.getValue().getFood().getRealprice());
            resorderitem.setFid(entry.getKey());
            resorderitem.setNum(entry.getValue().getNum());
            resorderitemDao.save(resorderitem);
        }
        return resorders.getRoid();
    }
}
