package com.tsp.new_tsp_project.api.admin.portfolio.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public interface AdminPortFolioService {

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioCnt
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	Integer getPortFolioCnt(ConcurrentHashMap<String, Object> searchMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioList
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	List<AdminPortFolioDTO> getPortFolioList(ConcurrentHashMap<String, Object> searchMap) throws Exception;
}
