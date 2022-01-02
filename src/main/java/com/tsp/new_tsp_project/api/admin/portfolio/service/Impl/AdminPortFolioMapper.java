package com.tsp.new_tsp_project.api.admin.portfolio.service.Impl;

import com.tsp.new_tsp_project.api.admin.portfolio.domain.dto.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.common.domain.dto.CommonImageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminPortFolioMapper {

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioCnt
	 * 2. ClassName  : AdminPortFolioMapper.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 수 조회
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 10.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	Integer getPortFolioCnt(Map<String, Object> searchMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioList
	 * 2. ClassName  : AdminPortFolioMapper.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 조회
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 10.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	List<AdminPortFolioDTO> getPortFolioList(Map<String, Object> searchMap) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioInfo
	 * 2. ClassName  : AdminPortFolioMapper.java
	 * 3. Comment    : 관리자 포트폴리오 상세 조회
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 10.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	Map<String, Object> getPortFolioInfo(AdminPortFolioDTO adminPortFolioDTO) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : insertPortFolio
	 * 2. ClassName  : AdminPortFolioMapper.java
	 * 3. Comment    : 관리자 포트폴리오 등록
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 11.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	Integer insertPortFolio(AdminPortFolioDTO adminPortFolioDTO) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : updatePortFolio
	 * 2. ClassName  : AdminPortFolioMapper.java
	 * 3. Comment    : 관리자 포트폴리오 수정
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 13.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	Integer updatePortFolio(AdminPortFolioDTO adminPortFolioDTO) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : getImageList
	 * 2. ClassName  : AdminPortFolioMapper.java
	 * 3. Comment    : 관리자 포트폴리오 이미지 조회
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 11.
	 * </pre>
	 *
	 * @param commonImageDTO
	 * @throws Exception
	 */
	List<CommonImageDTO> getImageList(CommonImageDTO commonImageDTO) throws Exception;

	/**
	 * <pre>
	 * 1. MethodName : deletePortFolio
	 * 2. ClassName  : AdminPortFolioMapper.java
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
	 * 2. ClassName  : AdminPortFolioMapper.java
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
	 * 2. ClassName  : AdminPortFolioMapper.java
	 * 3. Comment    : 관리자 포트폴리오 부분 삭제
	 * 4. 작성자       : Kim-in-su
	 * 5. 작성일       : 2021. 11. 17.
	 * </pre>
	 * @param portFolioMap
	 * @throws Exception
	 */
	Integer deletePartPortFolio(Map<String, Object> portFolioMap) throws Exception;
}
