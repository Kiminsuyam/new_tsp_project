package com.tsp.new_tsp_project.api.admin.production.service.impl;

import com.tsp.new_tsp_project.api.admin.production.service.AdminProductionApiService;
import com.tsp.new_tsp_project.api.admin.production.service.AdminProductionDTO;
import com.tsp.new_tsp_project.api.common.image.CommonImageDTO;
import com.tsp.new_tsp_project.api.common.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service("AdminProductionApiService")
@Transactional
@RequiredArgsConstructor
public class AdminProductionApiServiceImpl implements AdminProductionApiService {

	private final AdminProductionMapper adminProductionMapper;
	private final ImageService imageService;

	/**
	 * <pre>
	 * 1. MethodName : getProductionCnt
	 * 2. ClassName  : AdminProductionApiServiceImpl.java
	 * 3. Comment    : 관리자 프로덕션 갯수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	@Override
	public Integer getProductionCnt(ConcurrentHashMap<String, Object> searchMap) throws Exception {
		return this.adminProductionMapper.getProductionCnt(searchMap);
	}


	/**
	 * <pre>
	 * 1. MethodName : getProductionList
	 * 2. ClassName  : AdminProductionApiServiceImpl.java
	 * 3. Comment    : 관리자 프로덕션 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	@Override
	public List<AdminProductionDTO> getProductionList(ConcurrentHashMap<String, Object> searchMap) throws Exception {
		return this.adminProductionMapper.getProductionList(searchMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : getProductionInfo
	 * 2. ClassName  : AdminProductionApiServiceImpl.java
	 * 3. Comment    : 관리자 프로덕션 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminProductionDTO
	 * @throws Exception
	 */
	@Override
	public ConcurrentHashMap getProductionInfo(AdminProductionDTO adminProductionDTO) throws Exception {
		ConcurrentHashMap<String, Object> productionMap = new ConcurrentHashMap<>();

		productionMap.put("productionInfo", this.adminProductionMapper.getProductionInfo(adminProductionDTO));

		return productionMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : insertProduction
	 * 2. ClassName  : AdminProductionApiServiceImpl.java
	 * 3. Comment    : 관리자 프로덕션 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminProductionDTO
	 * @throws Exception
	 */
	@Override
	public Integer insertProduction(AdminProductionDTO adminProductionDTO,
									CommonImageDTO commonImageDTO,
									MultipartFile[] files) throws Exception {
		int num = 0;

		String flag = "insert";
		if(this.adminProductionMapper.insertProduction(adminProductionDTO) > 0) {
			commonImageDTO.setTypeName("production");
			commonImageDTO.setTypeIdx(adminProductionDTO.getIdx());
			if("Y".equals(this.imageService.uploadImageFile(commonImageDTO, files, flag))) {
				num = 1;
			} else {
				num = 0;
			}
		} else {
			num = 0;
		}
		return num;
	}

	/**
	 * <pre>
	 * 1. MethodName : updateProduction
	 * 2. ClassName  : AdminProductionApiServiceImpl.java
	 * 3. Comment    : 관리자 프로덕션 수정
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminProductionDTO
	 * @throws Exception
	 */
	@Override
	public Integer updateProduction(AdminProductionDTO adminProductionDTO,
									CommonImageDTO commonImageDTO,
									MultipartFile[] files) throws Exception {
		int num = 0;

		String flag = "update";

		if(this.adminProductionMapper.updateProduction(adminProductionDTO) > 0) {
			commonImageDTO.setTypeName("production");
			commonImageDTO.setTypeIdx(adminProductionDTO.getIdx());
			if("Y".equals(this.imageService.uploadImageFile(commonImageDTO, files, flag))) {
				num = 1;
			} else {
				num = 0;
			}
		} else {
			num = 0;
		}
		return num;
	}

	/**
	 * <pre>
	 * 1. MethodName : deleteProduction
	 * 2. ClassName  : AdminProductionApiServiceImpl.java
	 * 3. Comment    : 관리자 프로덕션 삭제
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 10. 05
	 * </pre>
	 *
	 * @param adminProductionDTO
	 * @throws Exception
	 */
	@Override
	public Integer deleteProduction(AdminProductionDTO adminProductionDTO) throws Exception {
		return this.adminProductionMapper.deleteProduction(adminProductionDTO);
	}
}
