package com.yc.webress.controller;

import com.yc.webress.Vo.JsonModel;
import com.yc.webress.bean.Resuser;
import com.yc.webress.service.ResuerBiz;
import com.yc.webress.utils.Constants;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@Api(description = "用户登录接口", tags = {"用户登录部分", "控制层"})
public class ResuserController {
    @Autowired
    private ResuerBiz resuerBiz;

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonModel logout(JsonModel jm, HttpSession session) {
        session.removeAttribute(Constants.LOGINUSER);
        jm.setCode(1);
        return jm;
    }


    @RequestMapping(value = "/checkLogin", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonModel checkLogout(JsonModel jm, HttpSession session) {
        if (session.getAttribute(Constants.LOGINUSER) == null) {
            jm.setCode(0);
            jm.setMsg("用户没有登录");
            return jm;
        } else {
            jm.setCode(1);
            Resuser resuser = (Resuser) session.getAttribute(Constants.LOGINUSER);
            jm.setObj(resuser);
        }
        return jm;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonModel login(JsonModel jm, HttpSession session, String valcode, String username, String pwd) {
        if (valcode == null || "".equals(valcode)) {
            jm.setCode(0);
            jm.setMsg("验证码不能为空");
            return jm;
        }
        String validateCode = (String) session.getAttribute("validateCode");//生成的标准验证码在valcodeController生成的存在session中
        if (!validateCode.equalsIgnoreCase(valcode)) {
            jm.setCode(0);
            jm.setMsg("验证码输入错误");
            return jm;
        }
        Resuser resuser = new Resuser();
        resuser.setUsername(username);
        resuser.setPwd(pwd);
        Resuser resuser1 = resuerBiz.login(resuser);
        if (resuser1 != null) {
            session.setAttribute(Constants.LOGINUSER, resuser1);//目前都是将当前用户的状态信息(登录状态，购物车，菜品都在session中)
            //更好的处理方案是用一个数据库/redis来保存
            jm.setCode(1);
            //再看地址
            if (session.getAttribute(Constants.LASTVISITEDADDR) != null) {
                jm.setUrl((String) session.getAttribute(Constants.LASTVISITEDADDR));
            } else {
                jm.setUrl(Constants.HOMEPAGE);
            }
        } else {
            jm.setCode(0);
            jm.setMsg("用户名或密码错误，请重试");
        }
        return jm;
    }

}
