package com.tsp.new_tsp_project.api.admin.model.controller;

import com.tsp.new_tsp_project.api.admin.model.service.AdminModelDTO;
import com.tsp.new_tsp_project.api.admin.model.service.AdminModelJpaDTO;
import com.tsp.new_tsp_project.api.admin.model.service.AdminModelService;
import com.tsp.new_tsp_project.api.common.SearchCommon;
import com.tsp.new_tsp_project.common.paging.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.rmi.ServerError;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequestMapping(value = "/api/jpa")
@RestController
@RequiredArgsConstructor
@Api(tags = "모델관련 API")
public class AdminModelJpa {

	private final AdminModelService adminModelService;
	private final SearchCommon searchCommon;

	@ApiOperation(value = "모델 조회", notes = "모델을 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@GetMapping(value = "/lists")
	public ConcurrentHashMap getModelList(Page page) throws Exception {
		// 페이징 및 검색
		ConcurrentHashMap modelMap = searchCommon.searchCommon(page, "");

		Integer modelListCnt = this.adminModelService.findModelsCount(modelMap);

		List<AdminModelJpaDTO> modelList = null;

		if(modelListCnt > 0) {
			modelList = this.adminModelService.findModels(modelMap);
		}

		// 리스트 수
		modelMap.put("pageSize", page.getSize());
		// 전체 페이지 수
		modelMap.put("perPageListCnt", Math.ceil((modelListCnt - 1) / page.getSize() + 1));
		// 전체 아이템 수
		modelMap.put("modelListTotalCnt", modelListCnt);

		modelMap.put("modelList", modelList);

		return modelMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : getMenModelEdit
	 * 2. ClassName  : AdminModelApi.java
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
	@GetMapping("/{categoryCd}/{idx}")
	public ConcurrentHashMap<String, Object> getMenModelEdit(@PathVariable("categoryCd") Integer categoryCd,
															 @PathVariable("idx") Integer idx) throws Exception {
		ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

		AdminModelJpaDTO adminModelJpaDTO = AdminModelJpaDTO.builder()
				.idx(idx)
				.categoryCd(categoryCd).build();

		resultMap.put("modelMap", this.adminModelService.findOneModel(adminModelJpaDTO));

		return resultMap;
	}
}
