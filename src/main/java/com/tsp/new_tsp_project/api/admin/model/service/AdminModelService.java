package com.tsp.new_tsp_project.api.admin.model.service;

import com.tsp.new_tsp_project.api.admin.model.service.impl.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminModelService {

	private final ModelRepository modelRepository;

	@Transactional
	public Integer findModelsCount(Map<String, Object> modelMap) throws Exception{
		return modelRepository.findModelsCount(modelMap);
	}

	@Transactional
	public List<AdminModelJpaDTO> findModels(Map<String, Object> modelMap) throws Exception{
		return modelRepository.findAll(modelMap);
	}

	@Transactional
	public Map<String, Object> findOneModel(AdminModelJpaDTO adminModelJpaDTO) throws Exception {
		return modelRepository.findOneModel(adminModelJpaDTO);
	}
}
