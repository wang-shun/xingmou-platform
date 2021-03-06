package com.gi.ctdn.dao;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.gi.ctdn.pojo.ProjectContact;

public interface ProjectContactDAO {

	List<ProjectContact> selectByProjectCode(@Param("projectCode") String sourceCode);

	List<ProjectContact> selectById(@Param("projectId") Integer projectId);

	List<ProjectContact> selectByOrgId(@Param("projectCode") String projectCode);
}