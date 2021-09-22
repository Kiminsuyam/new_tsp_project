package com.tsp.new_tsp_project.api.admin.portfolio.service.Impl;

import com.tsp.new_tsp_project.api.admin.portfolio.service.AdminPortFolioDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminPortFolioMapper {

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioCnt
	 * 2. ClassName  : AdminPortFolioMapper.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	Integer getPortFolioCnt(Map<String, Object> searchMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioList
	 * 2. ClassName  : AdminPortFolioMapper.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	List<AdminPortFolioDTO> getPortFolioList(Map<String, Object> searchMap) throws Exception;
}
