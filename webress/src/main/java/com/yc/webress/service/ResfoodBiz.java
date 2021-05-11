package com.yc.webress.service;

import com.yc.webress.bean.Resfood;

import java.util.List;

public interface ResfoodBiz {

    //查找所有的菜
    List<Resfood> findAll();

    //根据fid查找某个菜
    Resfood findByFid(Integer fid);
}
