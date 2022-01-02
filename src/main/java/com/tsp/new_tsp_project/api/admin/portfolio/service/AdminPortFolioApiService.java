package com.tsp.new_tsp_project.api.admin.portfolio.service;

import com.tsp.new_tsp_project.api.admin.portfolio.domain.dto.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.common.domain.dto.CommonImageDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public interface AdminPortFolioApiService {

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioCnt
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 수 조회
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 10.
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
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 10.
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
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 10.
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
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 11.
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
	 * 1. MethodName : updatePortFolio
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 수정
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 13.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @param commonImageDTO
	 * @param files
	 * @throws Exception
	 */
	Integer updatePortFolio(AdminPortFolioDTO adminPortFolioDTO,
							CommonImageDTO commonImageDTO,
							MultipartFile[] files,
							Map<String, Object> portFolioMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : deletePortFolio
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 삭제
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 14.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	Integer deletePortFolio(AdminPortFolioDTO adminPortFolioDTO) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : deleteAllPortFolio
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 전체 삭제
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 16.
	 * </pre>
	 *
	 * @throws Exception
	 */
	Integer deleteAllPortFolio(Map<String, Object> portFolioMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : deletePartPortFolio
	 * 2. ClassName  : AdminPortFolioService.java
	 * 3. Comment    : 관리자 포트폴리오 부분 삭제
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 17.
	 * </pre>
	 * @param portFolioMap
	 * @throws Exception
	 */
	Integer deletePartPortFolio(Map<String, Object> portFolioMap) throws Exception;

}
