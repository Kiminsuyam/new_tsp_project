package com.tsp.new_tsp_project.api.admin.portfolio.controller;

import com.tsp.new_tsp_project.api.admin.portfolio.domain.dto.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.admin.portfolio.service.jpa.AdminPortFolioJpaService;
import com.tsp.new_tsp_project.api.common.SearchCommon;
import com.tsp.new_tsp_project.common.paging.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/jpa-portfolio")
@Api(tags = "포트폴리오 관련 API")
public class AdminPortFolioJpaApi {

	private final AdminPortFolioJpaService adminPortFolioJpaService;
	private final SearchCommon searchCommon;

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioList
	 * 2. ClassName  : AdminPortFolio.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param page
	 * @throws Exception
	 */
	@ApiOperation(value = "포트폴리오 조회", notes = "포트폴리오를 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@GetMapping(value = "/lists")
	public ConcurrentHashMap getPortFolioList(Page page, @RequestParam(required = false) Map<String, Object> paramMap) throws Exception {
		ConcurrentHashMap<String, Object> portFolioMap = new ConcurrentHashMap<>();

		ConcurrentHashMap<String, Object> searchMap = searchCommon.searchCommon(page, paramMap);

		Integer portFolioCnt = this.adminPortFolioJpaService.findPortFolioCount(searchMap);

		List<AdminPortFolioDTO> portFolioList = new ArrayList<>();

		if(portFolioCnt > 0) {
			portFolioList = this.adminPortFolioJpaService.findPortFolioList(searchMap);
		}

		// 리스트 수
		portFolioMap.put("pageSize", page.getSize());
		// 전체 페이지 수
		portFolioMap.put("perPageListCnt", Math.ceil((portFolioCnt - 1) / page.getSize() + 1));
		// 전체 아이템 수
		portFolioMap.put("portFolioListCnt", portFolioCnt);

		portFolioMap.put("portFolioList", portFolioList);

		return portFolioMap;
	}
}
