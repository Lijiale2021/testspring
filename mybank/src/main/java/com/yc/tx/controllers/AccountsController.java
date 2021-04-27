package com.yc.tx.controllers;

import com.yc.tx.bean.Accounts;
import com.yc.tx.bean.OpTypes;
import com.yc.tx.service.AccountService;
import com.yc.tx.vo.AccountVo;
import com.yc.tx.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@Api(description = "银行账户操作接口",tags = {"账户操作接口","控制层"})
public class AccountsController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/openAccounts",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "开户",notes = "返回开户账号id")
    @ApiImplicitParams({@ApiImplicitParam(name = "money",value = "开户金额",required = true)})
    public @ResponseBody
    ResultVo openAccounts(AccountVo accountVo){
        log.info("用户请求开户，存入"+accountVo.getMoney());
        ResultVo rv=new ResultVo();
        try {
            Accounts a = new Accounts();
            double money = 1;
            if (accountVo.getMoney() != null && accountVo.getMoney() > 0) {
                money = accountVo.getMoney();
            }
            a.setBalance(money);
           Integer id=accountService.openAccount(a, money);
            a.setAccountId(id);
            rv.setCode(1);
            rv.setData(a);
        }catch (Exception exception){
            exception.printStackTrace();
            rv.setCode(0);
            rv.setMsg(exception.getMessage());
        }
        return rv;
    }

    @RequestMapping(value = "/deposite",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "存款",notes = "根据存款账号，存款金额发出存款操作，返回操作完成后新的余额.")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountId",value = "存款账号",required = true),
            @ApiImplicitParam(name = "money",value = "存款金额",required = true)})
    public @ResponseBody
    ResultVo deposite(AccountVo accountVo){
        log.info(accountVo.getAccountId()+"用户请求存入"+accountVo.getMoney());
        ResultVo rv=new ResultVo();
        try {
            Accounts a = new Accounts();
            double money = 0;
            int accountId=-1;
            if (accountVo.getAccountId()!=null&&accountVo.getAccountId()>0){
                accountId=accountVo.getAccountId();
            }
            if (accountVo.getMoney() != null && accountVo.getMoney() > 0) {
                money = accountVo.getMoney();
            }
            a.setAccountId(accountId);
            a=accountService.deposite(a,money, OpTypes.deposite.getName(),null);
            rv.setCode(1);
            rv.setData(a);
        }catch (Exception e){
            e.printStackTrace();
            rv.setCode(0);
            rv.setMsg(e.getMessage());
        }
        return rv;
    }

    @RequestMapping(value = "/withdraw",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "取款",notes = "根据取款账号，取款金额发出取款操作，返回操作完成后新的余额.")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountId",value = "取款账号",required = true),
            @ApiImplicitParam(name = "money",value = "取款金额",required = true)})
    public @ResponseBody
    ResultVo withdraw(AccountVo accountVo){
        log.info(accountVo.getAccountId()+"用户请求取款"+accountVo.getMoney());
        ResultVo rv=new ResultVo();
        try {
            Accounts a = new Accounts();
            double money = 0;
            int accountId=-1;
            if (accountVo.getAccountId()!=null&&accountVo.getAccountId()>0){
                accountId=accountVo.getAccountId();
            }
            if (accountVo.getMoney() != null && accountVo.getMoney() > 0) {
                money = accountVo.getMoney();
            }
            a.setAccountId(accountId);
            a=accountService.withdraw(a,money, OpTypes.withdraw.getName(),null);
            rv.setCode(1);
            rv.setData(a);
        }catch (Exception e){
            e.printStackTrace();
            rv.setCode(0);
            rv.setMsg(e.getMessage());
        }
        return rv;
    }

    @RequestMapping(value = "/transfer",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "转账",notes = "根据发起转账用户的账号和转账金额来给转入账号转入金额，然后返回余额.")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountId",value = "转出账号",required = true),
            @ApiImplicitParam(name = "money",value = "存款金额",required = true),
    @ApiImplicitParam(name = "inAccountId",value = "转入账号",required = true)})
    public @ResponseBody
    ResultVo transfer(AccountVo accountVo){
        log.info(accountVo.getAccountId()+"用户请求转账给"+ accountVo.getInAccountId()+"金额为"+accountVo.getMoney());
        ResultVo rv=new ResultVo();
        try {
            Accounts a1 = new Accounts();
            Accounts a2=new Accounts();
            double money = 0;
            int accountId=-1;
            int inAccountId=-1;
            if (accountVo.getAccountId()!=null&&accountVo.getAccountId()>0){
                accountId=accountVo.getAccountId();
            }
            if (accountVo.getInAccountId()!=null&&accountVo.getInAccountId()>0){
                inAccountId=accountVo.getInAccountId();
            }
            if (accountVo.getMoney() != null && accountVo.getMoney() > 0) {
                money = accountVo.getMoney();
            }
            a1.setAccountId(accountId);
            a2.setAccountId(inAccountId);
            a1=accountService.transfer(a2,a1,money);
            rv.setCode(1);
            rv.setData(a1);
        }catch (Exception e){
            e.printStackTrace();
            rv.setCode(0);
            rv.setMsg(e.getMessage());
        }
        return rv;
    }
    @RequestMapping(value = "/select",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "查询",notes = "根据用户的id,查询余额.")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountId",value = "查询账号",required = true)})
    public @ResponseBody
    ResultVo select(AccountVo accountVo){
        log.info(accountVo.getAccountId()+"用户请求查询余额");
        ResultVo rv=new ResultVo();
        try {
            Accounts a1 = new Accounts();
            int accountId=-1;
            if (accountVo.getAccountId()!=null&&accountVo.getAccountId()>0){
                accountId=accountVo.getAccountId();
            }
            a1.setAccountId(accountId);
            a1=accountService.showBalance(a1);
            rv.setCode(1);
            rv.setData(a1);
        }catch (Exception e){
            e.printStackTrace();
            rv.setCode(0);
            rv.setMsg(e.getMessage());
        }
        return rv;
    }

}
