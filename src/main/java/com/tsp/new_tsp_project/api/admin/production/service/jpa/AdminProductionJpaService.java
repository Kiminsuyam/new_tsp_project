package com.tsp.new_tsp_project.api.admin.production.service.jpa;

import com.tsp.new_tsp_project.api.admin.portfolio.domain.dto.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.admin.production.domain.dto.AdminProductionDTO;
import com.tsp.new_tsp_project.api.admin.production.domain.entity.AdminProductionEntity;
import com.tsp.new_tsp_project.api.admin.production.service.impl.jpa.ProductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AdminProductionJpaService {

	private final ProductionRepository productionRepository;

	/**
	 * <pre>
	 * 1. MethodName : findProductionCount
	 * 2. ClassName  : AdminProductionJpaService.java
	 * 3. Comment    : 관리자 프로덕션 리스트 갯수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param productionMap
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public Integer findProductionCount(ConcurrentHashMap<String, Object> productionMap) throws Exception {
		return this.productionRepository.findProductionCount(productionMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : findProductionList
	 * 2. ClassName  : AdminProductionJpaService.java
	 * 3. Comment    : 관리자 프로덕션 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param productionMap
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<AdminProductionDTO> findProductionList(Map<String, Object> productionMap) throws Exception {
		return productionRepository.findProductionList(productionMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : findOneProduction
	 * 2. ClassName  : AdminProductionJpaService.java
	 * 3. Comment    : 관리자 프로덕션 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminProductionEntity
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ConcurrentHashMap<String, Object> findOneProduction(AdminProductionEntity adminProductionEntity) throws Exception {
		return productionRepository.findOneProduction(adminProductionEntity);
	}
}
