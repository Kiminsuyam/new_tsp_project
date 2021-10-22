package com.tsp.new_tsp_project.api.admin.portfolio.service.Impl.jpa;

import com.tsp.new_tsp_project.api.admin.model.domain.dto.AdminModelDTO;
import com.tsp.new_tsp_project.api.admin.model.domain.entity.AdminModelEntity;
import com.tsp.new_tsp_project.api.admin.model.service.impl.jpa.ModelMapper;
import com.tsp.new_tsp_project.api.admin.portfolio.domain.dto.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.admin.portfolio.domain.entity.AdminPortFolioEntity;
import com.tsp.new_tsp_project.common.mapStruct.StructMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PortFolioMapper extends StructMapper<AdminPortFolioDTO, AdminPortFolioEntity> {

	PortFolioMapper INSTANCE = Mappers.getMapper(PortFolioMapper.class);

	@Override
	AdminPortFolioDTO toDto(AdminPortFolioEntity entity);

	@Override
	AdminPortFolioEntity toEntity(AdminPortFolioDTO dto);

	@Override
	List<AdminPortFolioDTO> toDtoList(List<AdminPortFolioEntity> entityList);

	@Override
	List<AdminPortFolioEntity> toEntityList(List<AdminPortFolioDTO> dtoList);
}
