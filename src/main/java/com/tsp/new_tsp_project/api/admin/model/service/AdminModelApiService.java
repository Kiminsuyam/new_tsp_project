package com.tsp.new_tsp_project.api.admin.model.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public interface AdminModelApiService {

	List<AdminModelDTO> getMenModelList(Map<String, Object> modelMap) throws Exception;

	ConcurrentHashMap<String, Object> getModelInfo(AdminModelDTO adminModelDTO) throws Exception;
}
