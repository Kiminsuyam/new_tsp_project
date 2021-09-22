package com.tsp.new_tsp_project.api.admin.production.service.impl;

import com.tsp.new_tsp_project.api.admin.production.service.AdminProductionApiService;
import com.tsp.new_tsp_project.api.admin.production.service.AdminProductionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service("AdminProductionApiService")
@RequiredArgsConstructor
public class AdminProductionApiServiceImpl implements AdminProductionApiService {

	private final AdminProductionMapper adminProductionMapper;

	/**
	 * <pre>
	 * 1. MethodName : getProductionCnt
	 * 2. ClassName  : AdminProductionApiServiceImpl.java
	 * 3. Comment    : 관리자 프로덕션 갯수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	@Override
	public Integer getProductionCnt(ConcurrentHashMap<String, Object> searchMap) throws Exception {
		return this.adminProductionMapper.getProductionCnt(searchMap);
	}


	/**
	 * <pre>
	 * 1. MethodName : getProductionList
	 * 2. ClassName  : AdminProductionApiServiceImpl.java
	 * 3. Comment    : 관리자 프로덕션 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	@Override
	public List<AdminProductionDTO> getProductionList(ConcurrentHashMap<String, Object> searchMap) throws Exception {
		return this.adminProductionMapper.getProductionList(searchMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : getProductionInfo
	 * 2. ClassName  : AdminProductionApiServiceImpl.java
	 * 3. Comment    : 관리자 프로덕션 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminProductionDTO
	 * @throws Exception
	 */
	@Override
	public ConcurrentHashMap getProductionInfo(AdminProductionDTO adminProductionDTO) throws Exception {
		ConcurrentHashMap<String, Object> productionMap = new ConcurrentHashMap<>();

		productionMap.put("productionInfo", this.adminProductionMapper.getProductionInfo(adminProductionDTO));

		return productionMap;
	}
}
