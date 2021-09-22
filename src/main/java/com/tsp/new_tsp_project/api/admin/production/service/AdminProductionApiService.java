package com.tsp.new_tsp_project.api.admin.production.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public interface AdminProductionApiService {

	/**
	 * <pre>
	 * 1. MethodName : getProductionCnt
	 * 2. ClassName  : AdminProductionApiService.java
	 * 3. Comment    : 관리자 프로덕션 갯수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	Integer getProductionCnt(ConcurrentHashMap<String, Object> searchMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : getProductionList
	 * 2. ClassName  : AdminProductionApiService.java
	 * 3. Comment    : 관리자 프로덕션 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	List<AdminProductionDTO> getProductionList(ConcurrentHashMap<String, Object> searchMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : getProductionInfo
	 * 2. ClassName  : AdminProductionApiService.java
	 * 3. Comment    : 관리자 프로덕션 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminProductionDTO
	 * @throws Exception
	 */
	ConcurrentHashMap<String, Object> getProductionInfo(AdminProductionDTO adminProductionDTO) throws Exception;
}
