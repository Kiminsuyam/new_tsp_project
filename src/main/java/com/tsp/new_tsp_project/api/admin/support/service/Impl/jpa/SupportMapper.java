package com.tsp.new_tsp_project.api.admin.support.service.Impl.jpa;

import com.tsp.new_tsp_project.api.admin.support.domain.dto.AdminSupportDTO;
import com.tsp.new_tsp_project.api.admin.support.domain.entity.AdminSupportEntity;
import com.tsp.new_tsp_project.common.mapStruct.StructMapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface SupportMapper extends StructMapper<AdminSupportDTO, AdminSupportEntity> {

	SupportMapper INSTANCE = Mappers.getMapper(SupportMapper.class);

	@Override
	AdminSupportDTO toDto(AdminSupportEntity entity);

	@Override
	AdminSupportEntity toEntity(AdminSupportDTO dto);

	@Override
	List<AdminSupportDTO> toDtoList(List<AdminSupportEntity> entityList);

	@Override
	List<AdminSupportEntity> toEntityList(List<AdminSupportDTO> dtoList);
}
