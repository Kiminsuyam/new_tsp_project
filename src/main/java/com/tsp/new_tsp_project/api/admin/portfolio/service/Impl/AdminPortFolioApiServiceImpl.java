package com.tsp.new_tsp_project.api.admin.portfolio.service.Impl;

import com.tsp.new_tsp_project.api.admin.portfolio.domain.dto.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.admin.portfolio.service.AdminPortFolioApiService;
import com.tsp.new_tsp_project.api.common.domain.dto.CommonImageDTO;
import com.tsp.new_tsp_project.api.common.image.service.ImageService;
import com.tsp.new_tsp_project.exception.ApiExceptionType;
import com.tsp.new_tsp_project.exception.TspException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service("AdminPortFolioApiService")
@Transactional
@RequiredArgsConstructor
public class AdminPortFolioApiServiceImpl implements AdminPortFolioApiService {

	private final AdminPortFolioMapper adminPortFolioMapper;
	private final ImageService imageService;

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioCnt
	 * 2. ClassName  : AdminPortFolioServiceImpl.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 조회
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 10.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	public Integer getPortFolioCnt(ConcurrentHashMap<String, Object> searchMap) throws Exception {
		return this.adminPortFolioMapper.getPortFolioCnt(searchMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioList
	 * 2. ClassName  : AdminPortFolioServiceImpl.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 조회
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 10.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	public List<AdminPortFolioDTO> getPortFolioList(ConcurrentHashMap<String, Object> searchMap) throws Exception {
		return this.adminPortFolioMapper.getPortFolioList(searchMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioInfo
	 * 2. ClassName  : AdminPortFolioServiceImpl.java
	 * 3. Comment    : 관리자 포트폴리오 상세 조회
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 10.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	public ConcurrentHashMap getPortFolioInfo(AdminPortFolioDTO adminPortFolioDTO) throws Exception {
		ConcurrentHashMap portFolioMap = new ConcurrentHashMap();

		CommonImageDTO commonImageDTO = new CommonImageDTO();
		commonImageDTO.setTypeIdx(adminPortFolioDTO.getIdx());
		commonImageDTO.setTypeName("portFolio");

		portFolioMap.put("portFolioInfo", this.adminPortFolioMapper.getPortFolioInfo(adminPortFolioDTO));
		portFolioMap.put("portFolioImageList", this.adminPortFolioMapper.getImageList(commonImageDTO));
		return portFolioMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : insertPortFolio
	 * 2. ClassName  : AdminPortFolioServiceImpl.java
	 * 3. Comment    : 관리자 포트폴리오 등록
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 11.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	public Integer insertPortFolio(AdminPortFolioDTO adminPortFolioDTO,
								   CommonImageDTO commonImageDTO,
								   MultipartFile[] files) throws Exception {

		int num;

		try {
			if(this.adminPortFolioMapper.insertPortFolio(adminPortFolioDTO) > 0) {
				commonImageDTO.setTypeName("portfolio");
				commonImageDTO.setTypeIdx(adminPortFolioDTO.getIdx());
				commonImageDTO.setVisible("Y");
//				commonImageDTO.builder().typeName("portfolio").typeIdx(adminPortFolioDTO.getIdx()).visible("Y").build();
				if("Y".equals(this.imageService.uploadImageFile(commonImageDTO, files, "insert"))) {
					num = 1;
				} else {
					throw new TspException(ApiExceptionType.NOT_EXIST_IMAGE);
				}
			} else {
				throw new TspException(ApiExceptionType.ERROR_PORTFOLIO);
			}
			return num;
		} catch (Exception e) {
			e.printStackTrace();
			throw new TspException(ApiExceptionType.ERROR_PORTFOLIO);
		}
	}

	/**
	 * <pre>
	 * 1. MethodName : updatePortFolio
	 * 2. ClassName  : AdminPortFolioServiceImpl.java
	 * 3. Comment    : 관리자 포트폴리오 수정
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 13.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	public Integer updatePortFolio(AdminPortFolioDTO adminPortFolioDTO,
								   CommonImageDTO commonImageDTO,
								   MultipartFile[] files,
								   Map<String, Object> portFolioMap) throws Exception {

		int num;

		try {
			if(this.adminPortFolioMapper.updatePortFolio(adminPortFolioDTO) > 0) {
				commonImageDTO.setTypeName("portfolio");
				commonImageDTO.setTypeIdx(adminPortFolioDTO.getIdx());
				commonImageDTO.setVisible("Y");
//				commonImageDTO.builder().typeName("portfolio").typeIdx(adminPortFolioDTO.getIdx()).visible("Y").build();
				if("Y".equals(this.imageService.updateMultipleFile(commonImageDTO, files, portFolioMap))) {
					num = 1;
				} else {
					throw new TspException(ApiExceptionType.NOT_EXIST_IMAGE);
				}
			} else {
				throw new TspException(ApiExceptionType.ERROR_PORTFOLIO);
			}	
		} catch (Exception e) {
			throw new TspException(ApiExceptionType.ERROR_PORTFOLIO);
		}
		return num;
	}

	/**
	 * <pre>
	 * 1. MethodName : deletePortFolio
	 * 2. ClassName  : AdminPortFolioServiceImpl.java
	 * 3. Comment    : 관리자 포트폴리오 삭제
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 14.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	public Integer deletePortFolio(AdminPortFolioDTO adminPortFolioDTO) throws Exception {
		return this.adminPortFolioMapper.deletePortFolio(adminPortFolioDTO);
	}

	/**
	 * <pre>
	 * 1. MethodName : deleteAllPortFolio
	 * 2. ClassName  : AdminPortFolioServiceImpl.java
	 * 3. Comment    : 관리자 포트폴리오 전체 삭제
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 16.
	 * </pre>
	 *
	 * @throws Exception
	 */
	public Integer deleteAllPortFolio(Map<String, Object> portFolioMap) throws Exception {
		return this.adminPortFolioMapper.deleteAllPortFolio(portFolioMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : deletePartPortFolio
	 * 2. ClassName  : AdminPortFolioServiceImpl.java
	 * 3. Comment    : 관리자 포트폴리오 부분 삭제
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 17.
	 * </pre>
	 * @param portFolioMap
	 * @throws Exception
	 */
	public Integer deletePartPortFolio(Map<String, Object> portFolioMap) throws Exception {
		return this.adminPortFolioMapper.deletePartPortFolio(portFolioMap);
	}
}
