package com.tsp.new_tsp_project.api.admin.production.controller;

import com.tsp.new_tsp_project.api.admin.production.domain.dto.AdminProductionDTO;
import com.tsp.new_tsp_project.api.admin.production.domain.entity.AdminProductionEntity;
import com.tsp.new_tsp_project.api.admin.production.service.jpa.AdminProductionJpaService;
import com.tsp.new_tsp_project.api.common.SearchCommon;
import com.tsp.new_tsp_project.common.paging.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/jpa-production")
@Api(tags = "프로덕션 관련 API")
public class AdminProductionJpaApi {

	private final SearchCommon searchCommon;
	private final AdminProductionJpaService adminProductionJpaService;

	/**
	 * <pre>
	 * 1. MethodName : getProductionList
	 * 2. ClassName  : AdminProductionJpaApi.java
	 * 3. Comment    : 관리자 프로덕션 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param page
	 * @throws Exception
	 */
	@ApiOperation(value = "프로덕션 조회", notes = "프로덕션을 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@GetMapping(value = "/lists")
	public ConcurrentHashMap getProductionList(Page page, @RequestParam(required = false) Map<String, Object> paramMap) throws Exception {
		ConcurrentHashMap<String, Object> productionMap = new ConcurrentHashMap<>();

		ConcurrentHashMap<String, Object> searchMap = searchCommon.searchCommon(page, paramMap);

		Integer portFolioCnt = this.adminProductionJpaService.findProductionCount(searchMap);

		List<AdminProductionDTO> portFolioList = new ArrayList<>();

		if(portFolioCnt > 0) {
			portFolioList = this.adminProductionJpaService.findProductionList(searchMap);
		}

		// 리스트 수
		productionMap.put("pageSize", page.getSize());
		// 전체 페이지 수
		productionMap.put("perPageListCnt", Math.ceil((portFolioCnt - 1) / page.getSize() + 1));
		// 전체 아이템 수
		productionMap.put("portFolioListCnt", portFolioCnt);

		productionMap.put("portFolioList", portFolioList);

		return productionMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : getProductInfo
	 * 2. ClassName  : AdminProductionJpaApi.java
	 * 3. Comment    : 관리자 프로덕션 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param idx
	 * @throws Exception
	 */
	@ApiOperation(value = "프로덕션 상세 조회", notes = "프로덕션을 상세 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@GetMapping(value = "/{idx}")
	public ConcurrentHashMap getProductionInfo(@PathVariable("idx") Integer idx) throws Exception {
		ConcurrentHashMap<String, Object> productionMap;

		AdminProductionEntity adminProductionEntity = AdminProductionEntity.builder().idx(idx).build();

		productionMap = this.adminProductionJpaService.findOneProduction(adminProductionEntity);

		return productionMap;
	}
}
