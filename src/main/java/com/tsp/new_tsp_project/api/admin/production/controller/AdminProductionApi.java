package com.tsp.new_tsp_project.api.admin.production.controller;

import com.tsp.new_tsp_project.api.admin.production.service.AdminProductionApiService;
import com.tsp.new_tsp_project.api.admin.production.service.AdminProductionDTO;
import com.tsp.new_tsp_project.api.common.SearchCommon;
import com.tsp.new_tsp_project.api.common.image.CommonImageDTO;
import com.tsp.new_tsp_project.common.paging.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.ServerError;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequestMapping(value = "/api/production")
@RestController
@RequiredArgsConstructor
@Api(tags = "프로덕션관련 API")
public class AdminProductionApi {

	private final AdminProductionApiService adminProductionApiService;
	private final SearchCommon searchCommon;

	/**
	 * <pre>
	 * 1. MethodName : getProductList
	 * 2. ClassName  : AdminProductionApi.java
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
	public ConcurrentHashMap getProductionList(Page page) throws Exception {

		ConcurrentHashMap<String, Object> productionMap = new ConcurrentHashMap<>();

		ConcurrentHashMap<String, Object> searchMap = searchCommon.searchCommon(page,"");

		Integer productionCnt = this.adminProductionApiService.getProductionCnt(searchMap);

		List<AdminProductionDTO> productionList = null;

		if(productionCnt > 0) {
			productionList = this.adminProductionApiService.getProductionList(searchMap);
		}

		// 리스트 수
		productionMap.put("pageSize", page.getSize());
		// 전체 페이지 수
		productionMap.put("perPageListCnt", Math.ceil((productionCnt - 1) / page.getSize() + 1));
		// 전체 아이템 수
		productionMap.put("productionListCnt", productionCnt);

		productionMap.put("productionList", productionList);

		return productionMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : getProductInfo
	 * 2. ClassName  : AdminProductionApi.java
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

		AdminProductionDTO adminProductionDTO = new AdminProductionDTO();
		adminProductionDTO.setIdx(idx);

		productionMap = this.adminProductionApiService.getProductionInfo(adminProductionDTO);

		return productionMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : insertProduction
	 * 2. ClassName  : AdminProductionApi.java
	 * 3. Comment    : 관리자 프로덕션 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminProductionDTO
	 * @throws Exception
	 */
	@ApiOperation(value = "프로덕션 등록", notes = "프로덕션을 등록한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public String insertProduction(AdminProductionDTO adminProductionDTO,
								   CommonImageDTO commonImageDTO,
								   @RequestParam(value="imageFiles", required=false) MultipartFile[] files) throws Exception {
		String result = "N";

		if(this.adminProductionApiService.insertProduction(adminProductionDTO, commonImageDTO, files) > 0) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}

	/**
	 * <pre>
	 * 1. MethodName : updateProduction
	 * 2. ClassName  : AdminProductionApi.java
	 * 3. Comment    : 관리자 프로덕션 수정
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminProductionDTO
	 * @throws Exception
	 */
	@ApiOperation(value = "프로덕션 수정", notes = "프로덕션을 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@PostMapping("/{idx}")
	public String updateProduction(@PathVariable("idx") Integer idx,
								   AdminProductionDTO adminProductionDTO) throws Exception {
		String result = "Y";

		adminProductionDTO.setIdx(idx);
		adminProductionDTO.setUpdater(1);
		if(this.adminProductionApiService.updateProduction(adminProductionDTO) > 0) {
			result = "Y";
		} else {
			result = "N";
		}

		return result;
	}
}
