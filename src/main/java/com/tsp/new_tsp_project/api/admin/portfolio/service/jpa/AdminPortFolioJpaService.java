package com.tsp.new_tsp_project.api.admin.portfolio.service.jpa;

import com.tsp.new_tsp_project.api.admin.model.domain.entity.AdminModelEntity;
import com.tsp.new_tsp_project.api.admin.portfolio.domain.dto.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.admin.portfolio.domain.entity.AdminPortFolioEntity;
import com.tsp.new_tsp_project.api.admin.portfolio.service.Impl.jpa.PortFolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AdminPortFolioJpaService {

	private final PortFolioRepository portFolioRepository;

	/**
	 * <pre>
	 * 1. MethodName : findPortFolioCount
	 * 2. ClassName  : AdminPortFolioJpaService.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 갯수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param searchMap
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public Integer findPortFolioCount(ConcurrentHashMap<String, Object> searchMap) throws Exception {
		return this.portFolioRepository.findPortFolioCount(searchMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : findPortFolioList
	 * 2. ClassName  : AdminPortFolioJpaService.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param portFolioMap
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<AdminPortFolioDTO> findPortFolioList(Map<String, Object> portFolioMap) throws Exception {
		return portFolioRepository.findPortFolioList(portFolioMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : findOnePortFolio
	 * 2. ClassName  : AdminPortFolioJpaService.java
	 * 3. Comment    : 관리자 포트폴리오 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminPortFolioEntity
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ConcurrentHashMap<String, Object> findOnePortFolio(AdminPortFolioEntity adminPortFolioEntity) throws Exception {
		return portFolioRepository.findOnePortFolio(adminPortFolioEntity);
	}
}
