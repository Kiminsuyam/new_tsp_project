package com.tsp.new_tsp_project.api.admin.portfolio.service.Impl;

import com.tsp.new_tsp_project.api.admin.portfolio.service.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.admin.portfolio.service.AdminPortFolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service("AdminPortFolioService")
@RequiredArgsConstructor
public class AdminPortFolioServiceImpl implements AdminPortFolioService {

	private final AdminPortFolioMapper adminPortFolioMapper;
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
}
