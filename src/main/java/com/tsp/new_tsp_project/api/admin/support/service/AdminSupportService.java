package com.tsp.new_tsp_project.api.admin.support.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public interface AdminSupportService {

	/**
	 * <pre>
	 * 1. MethodName : getSupportModelCnt
	 * 2. ClassName  : AdminSupportService.java
	 * 3. Comment    : 관리자 지원모델 수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 26.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	Integer getSupportModelCnt(ConcurrentHashMap<String, Object> searchMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : getSupportModelList
	 * 2. ClassName  : AdminSupportService.java
	 * 3. Comment    : 관리자 지원모델 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 26.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	List<AdminSupportDTO> getSupportModelList(ConcurrentHashMap<String, Object> searchMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : getSupportModelInfo
	 * 2. ClassName  : AdminSupportService.java
	 * 3. Comment    : 관리자 지원모델 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 26.
	 * </pre>
	 *
	 * @param adminSupportDTO
	 * @throws Exception
	 */
	ConcurrentHashMap<String, Object> getSupportModelInfo(AdminSupportDTO adminSupportDTO) throws Exception;
}
