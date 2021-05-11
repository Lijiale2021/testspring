package com.yc.webress.controller;

import com.yc.webress.Vo.CarItem;
import com.yc.webress.Vo.JsonModel;
import com.yc.webress.bean.Resfood;
import com.yc.webress.bean.Resorder;
import com.yc.webress.bean.Resuser;
import com.yc.webress.service.ResfoodBiz;
import com.yc.webress.service.ResorderBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.yc.webress.utils.Constants.*;

@RestController
@Slf4j
@Api(description = "点菜系统操作接口", tags = {"菜品操作部分", "控制层"})
public class ResfoodController {
    @Autowired
    private ResfoodBiz resfoodBiz;

    @Autowired
    private ResorderBiz resorderBiz;

    @RequestMapping(value = "confirmOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonModel confirmOrder(HttpSession session, Resorder resorder, JsonModel jm) {
        if (session.getAttribute(LOGINUSER) == null) {
            jm.setCode(0);
            jm.setMsg("用户未登陆");
            return jm;
        }

        //查询用户id 从session中取出登陆用户
        Resuser resuser = (Resuser) session.getAttribute(LOGINUSER);
        resorder.setUserid(resuser.getUserid());
        //准备 Resorderitem数据
        if (session.getAttribute(CART) == null || ((Map<Integer, CarItem>) session.getAttribute(CART)).size() < 0) {
            jm.setCode(0);
            jm.setMsg("用户未购买东西");
            return jm;
        }

        Map<Integer, CarItem> carItemMap = (Map<Integer, CarItem>) session.getAttribute(CART);
        try {
            resorderBiz.comleteOrder(resorder, carItemMap);
            session.removeAttribute(CART);
            jm.setCode(1);
        } catch (Exception e) {
            jm.setCode(0);
            jm.setMsg(e.getMessage());
        }
        return jm;
    }

    @RequestMapping(value = "/findAllFoods", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "查询所有的菜", notes = "查询所有的菜")
    public JsonModel findAllFoods(HttpSession session, JsonModel jm) {
        List<Resfood> list = resfoodBiz.findAll();
        session.setAttribute(RESFOODLIST, list);
        jm.setCode(1);
        jm.setObj(list);
        return jm;
    }

    @RequestMapping(value = "/findFoods", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "根据菜id来查找某商品", notes = "根据菜id来查找某商品")
    @ApiImplicitParams({@ApiImplicitParam(name = "fid", value = "菜的编号", required = true)
    })
    public JsonModel findFoods(HttpSession session, JsonModel jm, Resfood resfood) {
        List<Resfood> list = null;
        if (session.getAttribute(RESFOODLIST) != null) {
            list = (List<Resfood>) session.getAttribute(RESFOODLIST);
        } else {
            //没有则重新查一次
            list = resfoodBiz.findAll();
            session.setAttribute(RESFOODLIST, list);
        }
        for (Resfood f : list) {
            if (resfood.getFid().equals(f.getFid())) {
                jm.setCode(1);
                jm.setObj(f);
                return jm;
            }
        }
        jm.setCode(0);
        return jm;
    }
}
