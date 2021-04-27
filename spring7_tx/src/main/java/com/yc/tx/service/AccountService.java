package com.yc.tx.service;

import com.yc.tx.bean.Accounts;
import com.yc.tx.bean.OpRecord;

import java.util.List;

public interface AccountService {
    public Integer opeanAccount(Accounts accounts,double money);

    public Accounts deposite(Accounts accounts,double money,String optype,String transferid);

    public Accounts withdraw(Accounts accounts,double money,String optype,String transferid);

    public Accounts transfer(Accounts inAccount,Accounts outAccount,double money);

    public Accounts showBalance(Accounts accounts);

    public List<OpRecord> findById(Accounts accounts);

}
