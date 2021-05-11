package com.yc.webress.dao;

import com.yc.webress.bean.Resorderitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResorderitemDao extends JpaRepository<Resorderitem, Integer>, JpaSpecificationExecutor<Resorderitem> {

}
