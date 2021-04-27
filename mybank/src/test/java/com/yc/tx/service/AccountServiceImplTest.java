package com.yc.tx.service;

import com.yc.tx.bean.Accounts;
import com.yc.tx.bean.OpRecord;
import com.yc.tx.bean.OpTypes;
import com.yc.tx.dao.AccoutsDaoImpl;
import com.yc.tx.dao.OpRecordDaoImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceImplTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccoutsDaoImpl accoutsDao;

    @Autowired
    private Accounts accounts;

    @Autowired
    private OpRecordDaoImpl opRecordDao;

    @Autowired
    private OpRecord opRecord;

    @Autowired
    private AccountService accountService;

    @Test
    public void testDataSource() throws SQLException {
        Assert.assertNotNull(dataSource);
        System.out.println(dataSource.getConnection());
        //Initializing c3p0 pool... com.mchange.v2.c3p0.ComboPooledDataSource...表示c3p0的配置参数
    }

    @Test
    public void testAccountDaoImpl(){
        Assert.assertNotNull(accoutsDao);
    }

    @Test
    public void testsaveAccount(){
        accounts.setBalance(10.0);
        int accountid=accoutsDao.saveAccount(accounts);
        System.out.println("添加成功"+accountid);
    }

    @Test
    public void testupdateAccount(){
        accounts.setAccountId(3);
        accounts.setBalance(20.0);
        accoutsDao.updateAccount(accounts);
        System.out.println("修改成功");
    }

    @Test
    public void testfindAll(){
        List<Accounts> list=accoutsDao.findAll();
        System.out.println(list);
    }

    @Test
    public void testfind(){
        System.out.println(accoutsDao.findAccount(3));
    }

    @Test
    public void testsaveOpRecord(){
        opRecord.setAccountid(2);
        opRecord.setOpmoney(10.0);
        opRecord.setOptype(OpTypes.deposite.getName());//用枚举做这个值(约束值)，不容易出错.
        opRecord.setTransferid(UUID.randomUUID().toString());
        opRecordDao.saveOpRecord(opRecord);
        System.out.println("1");
    }

    @Test
    public void testfindopAll(){
        List<OpRecord> list=opRecordDao.findAll();
        System.out.println(list);
        //断言查出来的日志长度不会为0
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void testfindByAccountid(){
        List<OpRecord> list=opRecordDao.findByAccountid(2);
        Assert.assertNotEquals(0,list.size());
        System.out.println(list);
    }

    @Test
    public void testshowBlance(){
        accounts.setAccountId(1);
        System.out.println(accountService.showBalance(accounts));
    }

    @Test
    public void testopeanAccount(){
        accounts.setBalance(1.0);
        int i=accountService.openAccount(accounts,accounts.getBalance());
        System.out.println(i);
    }

    @Test
    public void testdeposite(){
        accounts.setAccountId(1);
        accounts=accountService.deposite(accounts,100,OpTypes.deposite.getName(), UUID.randomUUID().toString());
        System.out.println(accounts);
    }

    @Test
    public void testwithdraw(){
        accounts.setAccountId(1);
        accounts= accountService.withdraw(accounts, 100, OpTypes.withdraw.getName(), UUID.randomUUID().toString());
        System.out.println(accounts);
    }
    @Test
    public void testtransfer(){
        Accounts ina=new Accounts();
        ina.setAccountId(1);

        Accounts out=new Accounts();
        out.setAccountId(3);

        accounts=accountService.transfer(ina,out,5);
        System.out.println(accounts);
    }
}