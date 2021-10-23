package com.tsp.new_tsp_project.api.admin.production.service.impl.jpa;

import com.tsp.new_tsp_project.api.admin.model.service.impl.jpa.ModelMapper;
import com.tsp.new_tsp_project.api.admin.portfolio.domain.dto.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.admin.portfolio.domain.entity.AdminPortFolioEntity;
import com.tsp.new_tsp_project.api.admin.production.domain.dto.AdminProductionDTO;
import com.tsp.new_tsp_project.api.admin.production.domain.entity.AdminProductionEntity;
import com.tsp.new_tsp_project.common.mapStruct.StructMapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ProductionMapper extends StructMapper<AdminProductionDTO, AdminProductionEntity> {

	ProductionMapper INSTANCE = Mappers.getMapper(ProductionMapper.class);

	@Override
	AdminProductionDTO toDto(AdminProductionEntity entity);

	@Override
	AdminProductionEntity toEntity(AdminProductionDTO dto);

	@Override
	List<AdminProductionDTO> toDtoList(List<AdminProductionEntity> entityList);

	@Override
	List<AdminProductionEntity> toEntityList(List<AdminProductionDTO> dtoList);
}
