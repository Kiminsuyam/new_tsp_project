package com.tsp.new_tsp_project.api.admin.user.service.impl.jpa;

import com.tsp.new_tsp_project.api.admin.user.dto.AdminUserDTO;
import com.tsp.new_tsp_project.api.admin.user.entity.AdminUserEntity;
import com.tsp.new_tsp_project.common.mapStruct.StructMapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface UserMapper extends StructMapper<AdminUserDTO, AdminUserEntity> {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Override
	AdminUserDTO toDto(AdminUserEntity entity);

	@Override
	AdminUserEntity toEntity(AdminUserDTO dto);

	@Override
	List<AdminUserDTO> toDtoList(List<AdminUserEntity> entityList);

	@Override
	List<AdminUserEntity> toEntityList(List<AdminUserDTO> dtoList);
}
