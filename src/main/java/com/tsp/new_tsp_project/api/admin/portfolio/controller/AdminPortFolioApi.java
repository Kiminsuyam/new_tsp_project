package com.tsp.new_tsp_project.api.admin.portfolio.controller;

import com.tsp.new_tsp_project.api.admin.portfolio.service.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.admin.portfolio.service.AdminPortFolioService;
import com.tsp.new_tsp_project.api.common.NewCommonDTO;
import com.tsp.new_tsp_project.api.common.SearchCommon;
import com.tsp.new_tsp_project.common.paging.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.rmi.ServerError;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequestMapping(value = "/api/portfolio")
@RestController
@RequiredArgsConstructor
@Api(tags = "포트폴리오 관련 API")
public class AdminPortFolioApi {

	private final AdminPortFolioService adminPortFolioService;
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
	@PostMapping(value = "/lists")
	public ConcurrentHashMap getPortFolioList(Page page) throws Exception {
		ConcurrentHashMap<String, Object> portFolioMap = new ConcurrentHashMap<>();

		ConcurrentHashMap<String, Object> searchMap = searchCommon.searchCommon(page,"");

		Integer portFolioCnt = this.adminPortFolioService.getPortFolioCnt(searchMap);

		List<AdminPortFolioDTO> portFolioList = null;

		if(portFolioCnt > 0) {
			portFolioList = this.adminPortFolioService.getPortFolioList(searchMap);
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

	/**
	 * <pre>
	 * 1. MethodName : getPortFolioInfo
	 * 2. ClassName  : AdminPortFolio.java
	 * 3. Comment    : 관리자 포트폴리오 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param idx
	 * @throws Exception
	 */
	@ApiOperation(value = "포트폴리오 상세 조회", notes = "포트폴리오를 상세 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@PostMapping(value = "/{idx}")
	public ConcurrentHashMap getPortFolioInfo(@PathVariable("idx") Integer idx) throws Exception {
		ConcurrentHashMap<String, Object> portFolioMap;

		AdminPortFolioDTO adminPortFolioDTO = new AdminPortFolioDTO();
		adminPortFolioDTO.setIdx(idx);

		portFolioMap = this.adminPortFolioService.getPortFolioInfo(adminPortFolioDTO);

		return portFolioMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : insertPortFolio
	 * 2. ClassName  : AdminPortFolio.java
	 * 3. Comment    : 관리자 포트폴리오 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param request
	 * @param newCommonDTO
	 * @param adminPortFolioDTO
	 * @throws Exception
	 */
	@ApiOperation(value = "포트폴리오 등록", notes = "포트폴리오를 등록한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String insertPortFolio(HttpServletRequest request,
								  NewCommonDTO newCommonDTO,
							      AdminPortFolioDTO adminPortFolioDTO) throws Exception {
		String result = "N";

		searchCommon.giveAuth(request, newCommonDTO);

		if(this.adminPortFolioService.insertPortFolio(adminPortFolioDTO) > 0) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}
}
