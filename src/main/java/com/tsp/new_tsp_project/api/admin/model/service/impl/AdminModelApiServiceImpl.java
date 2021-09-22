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

	@Override
	public List<AdminModelDTO> getMenModelList(Map<String, Object> modelMap) throws Exception {
		return this.adminModelMapper.getMenModelList(modelMap);
	}

	@Override
	public ConcurrentHashMap<String, Object> getModelInfo(AdminModelDTO adminModelDTO) throws Exception {
		ConcurrentHashMap modelMap = new ConcurrentHashMap();
		modelMap.put("modelInfo", this.adminModelMapper.getModelInfo(adminModelDTO));
		return modelMap;
	}
}
