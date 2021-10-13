package com.tsp.new_tsp_project.api.admin.model.service;

import com.tsp.new_tsp_project.api.admin.model.service.impl.ModelRepository;
import com.tsp.new_tsp_project.api.common.image.service.CommonImageJpaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminModelService {

	private final ModelRepository modelRepository;

	/**
	 * <pre>
	 * 1. MethodName : findModelsCount
	 * 2. ClassName  : AdminModelService.java
	 * 3. Comment    : 관리자 모델 수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param modelMap
	 * @throws Exception
	 */
	@Transactional
	public Integer findModelsCount(Map<String, Object> modelMap) throws Exception{
		return modelRepository.findModelsCount(modelMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : findModels
	 * 2. ClassName  : AdminModelService.java
	 * 3. Comment    : 관리자 모델 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param modelMap
	 * @throws Exception
	 */
	@Transactional
	public List<AdminModelJpaDTO> findModels(Map<String, Object> modelMap) throws Exception{
		return modelRepository.findAll(modelMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : findOneModel
	 * 2. ClassName  : AdminModelService.java
	 * 3. Comment    : 관리자 모델 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminModelJpaDTO
	 * @throws Exception
	 */
	@Transactional
	public Map<String, Object> findOneModel(AdminModelJpaDTO adminModelJpaDTO) throws Exception {
		return modelRepository.findOneModel(adminModelJpaDTO);
	}

	/**
	 * <pre>
	 * 1. MethodName : insertModel
	 * 2. ClassName  : AdminModelService.java
	 * 3. Comment    : 관리자 모델 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminModelJpaDTO
	 * @throws Exception
	 */
	@Transactional
	public Integer insertModel(AdminModelJpaDTO adminModelJpaDTO, CommonImageJpaDTO commonImageJpaDTO, MultipartFile[] files) throws Exception {
		return modelRepository.insertModel(adminModelJpaDTO, commonImageJpaDTO, files);
	}

	/**
	 * <pre>
	 * 1. MethodName : updateModel
	 * 2. ClassName  : AdminModelService.java
	 * 3. Comment    : 관리자 모델 수정
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminModelJpaDTO
	 * @throws Exception
	 */
	@Transactional
	public Integer updateModel(AdminModelJpaDTO adminModelJpaDTO, CommonImageJpaDTO commonImageJpaDTO, MultipartFile[] files) throws Exception {
		return modelRepository.updateModel(adminModelJpaDTO, commonImageJpaDTO, files);
	}
}
