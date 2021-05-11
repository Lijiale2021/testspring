package com.yc.webress.Vo;

import com.yc.webress.bean.Resfood;

import java.io.Serializable;
import java.math.BigDecimal;


public class CarItem implements Serializable {

    private static final long serialVersionUID = -3712389680829191300L;
    private Resfood food;
    private int num;
    private BigDecimal smallCount;

    public BigDecimal getSmallCount() {
        this.smallCount = food.getRealprice().multiply(new BigDecimal(num));
        return smallCount;
    }

    public Resfood getFood() {
        return food;
    }

    public int getNum() {
        return num;
    }

    public void setFood(Resfood food) {
        this.food = food;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
