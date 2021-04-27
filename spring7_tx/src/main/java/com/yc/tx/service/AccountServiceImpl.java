package com.yc.tx.service;

import com.yc.tx.bean.Accounts;
import com.yc.tx.bean.OpRecord;
import com.yc.tx.bean.OpTypes;
import com.yc.tx.dao.AccoutsDao;
import com.yc.tx.dao.OpRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service//完成了异常转换可以把Exception转换成RuntimeException
@Transactional(propagation= Propagation.REQUIRED,//事务的传播
        isolation= Isolation.DEFAULT,//隔离性
        readOnly = false,//只读
        timeout  =100,//超时
        rollbackForClassName = {"RuntimeException"})//发生异常时回滚
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccoutsDao accoutsDao;

    @Autowired
    private OpRecordDao opRecordDao;

    @Override
    public Integer opeanAccount(Accounts accounts, double money) {
        int i= accoutsDao.saveAccount(accounts);
        OpRecord r=new OpRecord();
        r.setAccountid(i);
        r.setOpmoney(money);
        r.setOptime(new Timestamp(System.currentTimeMillis()));
        r.setOptype(OpTypes.deposite.getName());
        opRecordDao.saveOpRecord(r);
        return accounts.getAccountId();
    }

    @Override
    public Accounts deposite(Accounts accounts, double money, String optype, String transferid) {
        Accounts a=this.showBalance(accounts);
        a.setBalance(a.getBalance()+money);
        accoutsDao.updateAccount(a);
        OpRecord r=new OpRecord();
        r.setAccountid(accounts.getAccountId());
        r.setOpmoney(money);
        r.setOptime(new Timestamp(System.currentTimeMillis()));
        r.setOptype(optype);
        if (optype.contentEquals(OpTypes.transfer.getName())){
            r.setTransferid(transferid);
        }else {
            r.setTransferid("");
        }
        opRecordDao.saveOpRecord(r);
        return a;
    }

    @Override
    @Transactional
    public Accounts withdraw(Accounts accounts, double money,String optype,String transferid) {
        Accounts a=this.showBalance(accounts);
        a.setBalance(a.getBalance()-money);
        accoutsDao.updateAccount(a);
        OpRecord r=new OpRecord();
        r.setAccountid(accounts.getAccountId());
        r.setOpmoney(money);
        r.setOptime(new Timestamp(System.currentTimeMillis()));
        r.setOptype(optype);
        if (optype.contentEquals(OpTypes.transfer.getName())){
            r.setTransferid(transferid);
        }else {
            r.setTransferid("");
        }
        opRecordDao.saveOpRecord(r);
        return a;
    }

    @Override
    public Accounts transfer(Accounts inAccount, Accounts outAccount, double money) {
        String tid= UUID.randomUUID().toString();
        this.deposite(inAccount,money,OpTypes.transfer.getName(), tid);
        Accounts a=this.withdraw(outAccount,money,OpTypes.transfer.getName(), tid);
        return a;
    }

    @Override
    @Transactional(readOnly = true)
    public Accounts showBalance(Accounts accounts) {
        return accoutsDao.findAccount(accounts.getAccountId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpRecord> findById(Accounts accounts) {
        return opRecordDao.findByAccountid(accounts.getAccountId());
    }
}
