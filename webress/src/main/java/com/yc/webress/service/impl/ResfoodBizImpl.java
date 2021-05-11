package com.yc.webress.service.impl;

import com.yc.webress.bean.Resfood;
import com.yc.webress.dao.ResfoodDao;
import com.yc.webress.service.ResfoodBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResfoodBizImpl implements ResfoodBiz {
    @Autowired
    private ResfoodDao resfoodDao;
    @Autowired
    private Resfood resfood;

    //查找所有的菜
    @Override
    @Transactional(readOnly = true)
    public List<Resfood> findAll() {
        return resfoodDao.findAll();
    }

    //根据fid查找某个菜
    @Override
    @Transactional(readOnly = true)
    public Resfood findByFid(Integer fid) {
        resfood.setFid(fid);
        Example<Resfood> example = Example.of(resfood);
        Optional<Resfood> optional = resfoodDao.findOne(example);
        return optional.get();
    }
}
