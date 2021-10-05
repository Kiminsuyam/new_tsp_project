package com.tsp.new_tsp_project.api.admin.portfolio.service.Impl;

import com.tsp.new_tsp_project.api.admin.portfolio.service.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.admin.portfolio.service.AdminPortFolioApiService;
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
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
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
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
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
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	public ConcurrentHashMap getPortFolioInfo(AdminPortFolioDTO adminPortFolioDTO) throws Exception {
		ConcurrentHashMap portFolioMap = new ConcurrentHashMap();
		portFolioMap.put("portFolioInfo", this.adminPortFolioMapper.getPortFolioInfo(adminPortFolioDTO));
		return portFolioMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : insertPortFolio
	 * 2. ClassName  : AdminPortFolioServiceImpl.java
	 * 3. Comment    : 관리자 포트폴리오 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	public Integer insertPortFolio(AdminPortFolioDTO adminPortFolioDTO,
								   CommonImageDTO commonImageDTO,
								   MultipartFile[] files) throws Exception {

		int num = 0;

		String flag = "insert";

		if("0".equals(adminPortFolioDTO.getCategoryCd())) {
			adminPortFolioDTO.setCategoryNm("광고");
		} else if("1".equals(adminPortFolioDTO.getCategoryCd())) {
			adminPortFolioDTO.setCategoryNm("패션쇼");
		} else if("2".equals(adminPortFolioDTO.getCategoryCd())) {
			adminPortFolioDTO.setCategoryNm("패션필름");
		} else if("3".equals(adminPortFolioDTO.getCategoryCd())) {
			adminPortFolioDTO.setCategoryNm("뮤직비디오");
		}
		if(this.adminPortFolioMapper.insertPortFolio(adminPortFolioDTO) > 0) {
			commonImageDTO.setTypeName("portFolio");
			commonImageDTO.setTypeIdx(adminPortFolioDTO.getIdx());
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
	 * 1. MethodName : updatePortFolio
	 * 2. ClassName  : AdminPortFolioServiceImpl.java
	 * 3. Comment    : 관리자 포트폴리오 수정
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	public Integer updatePortFolio(AdminPortFolioDTO adminPortFolioDTO,
								   CommonImageDTO commonImageDTO,
								   MultipartFile[] files) throws Exception {

		int num = 0;

		String flag = "update";

		if("0".equals(adminPortFolioDTO.getCategoryCd())) {
			adminPortFolioDTO.setCategoryNm("광고");
		} else if("1".equals(adminPortFolioDTO.getCategoryCd())) {
			adminPortFolioDTO.setCategoryNm("패션쇼");
		} else if("2".equals(adminPortFolioDTO.getCategoryCd())) {
			adminPortFolioDTO.setCategoryNm("패션필름");
		} else if("3".equals(adminPortFolioDTO.getCategoryCd())) {
			adminPortFolioDTO.setCategoryNm("뮤직비디오");
		}
		if(this.adminPortFolioMapper.updatePortFolio(adminPortFolioDTO) > 0) {
			commonImageDTO.setTypeName("portFolio");
			commonImageDTO.setTypeIdx(adminPortFolioDTO.getIdx());
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
}