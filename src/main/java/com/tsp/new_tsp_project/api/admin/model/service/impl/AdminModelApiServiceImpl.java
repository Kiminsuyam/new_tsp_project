package com.tsp.new_tsp_project.api.admin.model.service.impl;

import com.tsp.new_tsp_project.api.admin.model.service.AdminModelApiService;
import com.tsp.new_tsp_project.api.admin.model.service.AdminModelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("AdminModelApiService")
@RequiredArgsConstructor
public class AdminModelApiServiceImpl implements AdminModelApiService {

	private final AdminModelMapper adminModelMapper;

	/**
	 * <pre>
	 * 1. MethodName : getModelListCnt
	 * 2. ClassName  : AdminModelApiServiceImpl.java
	 * 3. Comment    : 관리자 모델 수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param modelMap
	 * @throws Exception
	 */
	@Override
	public Integer getModelListCnt(Map<String, Object> modelMap) throws Exception {
		return this.adminModelMapper.getModelListCnt(modelMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : getModelList
	 * 2. ClassName  : AdminModelApiServiceImpl.java
	 * 3. Comment    : 관리자 모델 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param modelMap
	 * @throws Exception
	 */
	@Override
	public List<AdminModelDTO> getModelList(Map<String, Object> modelMap) throws Exception {
		return this.adminModelMapper.getModelList(modelMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : getModelInfo
	 * 2. ClassName  : AdminModelApiServiceImpl.java
	 * 3. Comment    : 관리자 모델 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminModelDTO
	 * @throws Exception
	 */
	@Override
	public ConcurrentHashMap<String, Object> getModelInfo(AdminModelDTO adminModelDTO) throws Exception {
		ConcurrentHashMap modelMap = new ConcurrentHashMap();
		modelMap.put("modelInfo", this.adminModelMapper.getModelInfo(adminModelDTO));
		return modelMap;
	}
}
