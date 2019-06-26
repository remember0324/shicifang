package com.tensquare.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.base.pojo.Label;
import org.springframework.stereotype.Repository;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
@Repository
public interface LabelDao extends JpaRepository<Label,String>,JpaSpecificationExecutor<Label>{
	
}
