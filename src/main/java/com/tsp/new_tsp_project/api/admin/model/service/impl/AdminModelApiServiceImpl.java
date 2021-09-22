package com.tsp.new_tsp_project.api.admin.model.service.impl;

import com.tsp.new_tsp_project.api.admin.model.service.AdminModelApiService;
import com.tsp.new_tsp_project.api.admin.model.service.AdminModelDTO;
import com.tsp.new_tsp_project.api.common.NewCommonDTO;
import com.tsp.new_tsp_project.api.common.SearchCommon;
import com.tsp.new_tsp_project.api.common.image.CommonImageDTO;
import com.tsp.new_tsp_project.api.common.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("AdminModelApiService")
@RequiredArgsConstructor
public class AdminModelApiServiceImpl implements AdminModelApiService {

	private final AdminModelMapper adminModelMapper;
	private final ImageService imageService;

	/**
	 * <pre>
	 * 1. MethodName : getModelListCnt
	 * 2. ClassName  : AdminModelApiServiceImpl.java
	 * 3. Comment    : 관리자 모델 수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param modelMap
	 * @throws Exception
	 */
	@Override
	public Integer getModelListCnt(Map<String, Object> modelMap) throws Exception {
		return this.adminModelMapper.getModelListCnt(modelMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : getModelList
	 * 2. ClassName  : AdminModelApiServiceImpl.java
	 * 3. Comment    : 관리자 모델 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param modelMap
	 * @throws Exception
	 */
	@Override
	public List<AdminModelDTO> getModelList(Map<String, Object> modelMap) throws Exception {
		return this.adminModelMapper.getModelList(modelMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : getModelInfo
	 * 2. ClassName  : AdminModelApiServiceImpl.java
	 * 3. Comment    : 관리자 모델 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminModelDTO
	 * @throws Exception
	 */
	@Override
	public ConcurrentHashMap<String, Object> getModelInfo(AdminModelDTO adminModelDTO) throws Exception {
		ConcurrentHashMap modelMap = new ConcurrentHashMap();
		modelMap.put("modelInfo", this.adminModelMapper.getModelInfo(adminModelDTO));
		return modelMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : addMenModel
	 * 2. ClassName  : AdminModelApiServiceImpl.java
	 * 3. Comment    : 관리자 남자 모델 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminModelDTO
	 * @param commonImageDTO
	 * @param fileName
	 * @throws Exception
	 */
	public String addMenModel(AdminModelDTO adminModelDTO,
							  CommonImageDTO commonImageDTO,
							  MultipartFile[] fileName) throws Exception {
		String result = "N";

		adminModelDTO.setCategoryCd("1");
		adminModelDTO.setCategoryNm("men");
		if("Y".equals(this.imageService.uploadImageFile(adminModelDTO, commonImageDTO, fileName))) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
}
