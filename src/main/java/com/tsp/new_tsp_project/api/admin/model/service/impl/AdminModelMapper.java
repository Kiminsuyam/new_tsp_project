package com.tsp.new_tsp_project.api.admin.model.service.impl;

import com.tsp.new_tsp_project.api.admin.model.service.AdminModelDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminModelMapper {
	List<AdminModelDTO> getMenModelList(Map<String, Object> commandMap) throws  Exception;

	Map<String, Object> getModelInfo(AdminModelDTO adminModelDTO) throws Exception;
}
