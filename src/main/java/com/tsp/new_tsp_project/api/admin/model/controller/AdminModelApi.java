package com.tsp.new_tsp_project.api.admin.model.controller;

import com.tsp.new_tsp_project.api.admin.model.service.AdminModelApiService;
import com.tsp.new_tsp_project.api.admin.model.service.AdminModelDTO;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequestMapping(value = "/api/model")
@RestController
@RequiredArgsConstructor
@Api(tags = "모델관련 API")
public class AdminModelApi {

	private final AdminModelApiService adminModelApiService;
	private final SearchCommon searchCommon;

	/**
	 * <pre>
	 * 1. MethodName : lists
	 * 2. ClassName  : AdminModelApi.java
	 * 3. Comment    : 관리자 모델 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param page
	 * @throws Exception
	 */
	@ApiOperation(value = "모델 조회", notes = "모델을 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@GetMapping(value = "/lists")
	public List<AdminModelDTO> getUserList(Page page) throws Exception {
		// 페이징 및 검색
		ConcurrentHashMap modelMap = searchCommon.searchCommon(page, "");

		List<AdminModelDTO> modelList = this.adminModelApiService.getMenModelList(modelMap);

		return modelList;
	}

	/**
	 * <pre>
	 * 1. MethodName : getModelEdit
	 * 2. ClassName  : AdminUserApi.java
	 * 3. Comment    : 관리자 남자 모델 상세
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param idx
	 * @throws Exception
	 */
	@ApiOperation(value = "모델 상세 조회", notes = "모델을 상세 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@GetMapping("/{idx}")
	public ConcurrentHashMap<String, Object> getModelEdit(@PathVariable("idx") Integer idx) throws Exception {
		ConcurrentHashMap<String, Object> modelMap = new ConcurrentHashMap<>();

		AdminModelDTO adminModelDTO = new AdminModelDTO();
		adminModelDTO.setIdx(idx);

		modelMap = this.adminModelApiService.getModelInfo(adminModelDTO);

		return modelMap;
	}
}
