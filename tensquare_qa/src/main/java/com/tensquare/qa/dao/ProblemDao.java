package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    @Query(value = "SELECT p.* FROM tb_problem p LEFT JOIN tb_pl pl ON p.id=pl.problemid WHERE pl.labelid=? ORDER BY p.replytime DESC", nativeQuery = true)
    Page<Problem> newProblems(String lableId, Pageable pageable);

    @Query(value = "SELECT p.* FROM tb_problem p LEFT JOIN tb_pl pl ON p.id = pl.problemid WHERE pl.labelid = ? ORDER BY p.thumbup DESC", nativeQuery = true)
    Page<Problem> hotList(String lableId, Pageable pageable);

    @Query(value = "SELECT p.* FROM tb_problem p LEFT JOIN tb_pl pl ON p.id = pl.problemid WHERE pl.labelid = ? and p.reply=0", nativeQuery = true)
    Page<Problem> waitlist(String lableId, Pageable pageable);
}
