package com.yc.webress.service.impl;

import com.yc.webress.bean.Resuser;
import com.yc.webress.dao.ResuerDao;
import com.yc.webress.service.ResuerBiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Supplier;

@Service

public class ResuerBizImpl implements ResuerBiz {
    @Autowired
    private ResuerDao resuerDao;

    //登录
    @Override
    @Transactional(readOnly = true)
    public Resuser login(Resuser resuser) {
        Example<Resuser> example = Example.of(resuser);
        Optional<Resuser> optional = resuerDao.findOne(example);
        return optional.orElseGet(new Supplier<Resuser>() {
            @Override
            public Resuser get() {
                return null;
            }
        });
    }
}
