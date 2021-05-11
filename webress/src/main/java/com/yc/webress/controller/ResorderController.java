package com.yc.webress.controller;

import com.yc.webress.Vo.CarItem;
import com.yc.webress.Vo.JsonModel;
import com.yc.webress.bean.Resfood;
import com.yc.webress.service.ResfoodBiz;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.yc.webress.utils.Constants.CART;
import static com.yc.webress.utils.Constants.RESFOODLIST;

@RestController
@Slf4j
@Api(description = "下订单接口", tags = {"用户下订单部分", "控制层"})
public class ResorderController {

    @Autowired
    private ResfoodBiz resfoodBiz;

    //取到购物车里面的商品信息
    @RequestMapping(value = "/getCarInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonModel getCarInfo(HttpServletRequest req, HttpSession session, JsonModel jm) {
        List<CarItem> list = new ArrayList<>();
        if (session.getAttribute(CART) != null) {
            jm.setCode(1);
            //变更 将对象改为list 方便后面循环取值
            Map<Integer, CarItem> map = null;
            map = (Map<Integer, CarItem>) session.getAttribute(CART);
            Set<Integer> set = map.keySet();
            Iterator<Integer> it = set.iterator();
            while (it.hasNext()) {
                int x = it.next();
                list.add(map.get(x));
            }
        } else {
            jm.setCode(0);
            jm.setMsg("失败");
        }
        jm.setObj(list);
        return jm;
    }

    //删除购物车中某一项商品
    @RequestMapping(value = "/delorder", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonModel delorder(HttpSession session, JsonModel jm, Resfood resfood) {
        int fid = resfood.getFid();

        Map<Integer, CarItem> map = null;
        if (session.getAttribute(CART) != null) {
            map = (Map<Integer, CarItem>) session.getAttribute(CART);
        } else {
            map = new HashMap<Integer, CarItem>();
        }
        if (map.containsKey(fid)) {
            map.remove(fid);
            jm.setCode(1);
        } else {
            jm.setCode(0);
        }
        session.setAttribute(CART, map);
        return jm;
    }

    //清空购物车
    @RequestMapping(value = "/clearAll", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonModel clearAll(HttpSession session, JsonModel jm) {
        session.removeAttribute(CART);
        jm.setCode(1);
        return jm;
    }

    //
    @RequestMapping(value = "/orderJson", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonModel orderJson(HttpServletRequest req, HttpSession session, JsonModel jm) {
        commonOrder(req, session);
        jm.setCode(1);
        return jm;
    }

    private void commonOrder(HttpServletRequest req, HttpSession session) {
        //取出要加入购物车的商品id和数量
        int fid = Integer.parseInt(req.getParameter("fid"));
        int num = Integer.parseInt(req.getParameter("num"));
        //取出session中的商品
        List<Resfood> list = null;
        if (session.getAttribute(RESFOODLIST) != null) {
            list = (List<Resfood>) session.getAttribute(RESFOODLIST);
        } else {
            //没有，则查
            list = resfoodBiz.findAll();
            session.setAttribute(RESFOODLIST, list);
        }
        //取出目标商品
        Resfood food = null;
        for (Resfood f : list) {
            if (f.getFid() == fid) {
                food = f;
                break;
            }
        }
        //购物车跟用户关联，所以存session
        Map<Integer, CarItem> cart = null;
        if (session.getAttribute(CART) != null) {
            cart = (Map<Integer, CarItem>) session.getAttribute(CART);
        } else {
            cart = new HashMap<>();
        }
        //看这个购物车是否有fid
        CarItem ci = null;
        if (cart.containsKey(fid)) {
            //证明用户已经购买了这个菜，则数量增加
            ci = cart.get(fid);
            int newnum = ci.getNum() + num;
            ci.setNum(newnum);
        } else {
            //用户还没买过则新创建一个carItem存到map中
            ci = new CarItem();
            ci.setFood(food);
            ci.setNum(num);
        }
        //如果商品数量最后少于零则在购物车中删除
        if (ci.getNum() <= 0) {
            cart.remove(fid);
        } else {
            ci.getSmallCount();//计算小计
            //将cartitem存到map中
            cart.put(fid, ci);
        }
        //将cart存到session中
        session.setAttribute(CART, cart);
    }
}
