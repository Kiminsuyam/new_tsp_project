package com.tsp.new_tsp_project.api.admin.portfolio.service;

import com.tsp.new_tsp_project.api.common.image.CommonImageDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public interface AdminPortFolioApiService {

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioCnt
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	Integer getPortFolioCnt(ConcurrentHashMap<String, Object> searchMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioList
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	List<AdminPortFolioDTO> getPortFolioList(ConcurrentHashMap<String, Object> searchMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioInfo
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	ConcurrentHashMap<String, Object> getPortFolioInfo(AdminPortFolioDTO adminPortFolioDTO) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : insertPortFolio
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @param commonImageDTO
	 * @param files
	 * @throws Exception
	 */
	Integer insertPortFolio(AdminPortFolioDTO adminPortFolioDTO,
							CommonImageDTO commonImageDTO,
							MultipartFile[] files) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : updatePortFolie
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 수정
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @param commonImageDTO
	 * @param files
	 * @throws Exception
	 */
	Integer updatePortFolio(AdminPortFolioDTO adminPortFolioDTO,
							CommonImageDTO commonImageDTO,
							MultipartFile[] files) throws Exception;
}