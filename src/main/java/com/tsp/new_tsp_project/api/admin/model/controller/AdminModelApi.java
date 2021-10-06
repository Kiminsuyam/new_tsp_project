package com.tsp.new_tsp_project.api.admin.model.controller;

import com.tsp.new_tsp_project.api.admin.model.service.AdminModelApiService;
import com.tsp.new_tsp_project.api.admin.model.service.AdminModelDTO;
import com.tsp.new_tsp_project.api.common.NewCommonDTO;
import com.tsp.new_tsp_project.api.common.image.CommonImageDTO;
import com.tsp.new_tsp_project.api.common.SearchCommon;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
	public ConcurrentHashMap getModelList(Page page) throws Exception {
		// 페이징 및 검색
		ConcurrentHashMap modelMap = searchCommon.searchCommon(page, "");

		Integer modelListCnt = this.adminModelApiService.getModelListCnt(modelMap);

		List<AdminModelDTO> modelList = null;

		if(modelListCnt > 0) {
			modelList = this.adminModelApiService.getModelList(modelMap);
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
	 * 1. MethodName : insertMenModel
	 * 2. ClassName  : AdminModelApi.java
	 * 3. Comment    : 관리자 남자 모델 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param fileName
	 * @param adminModelDTO
	 * @throws Exception
	 */
	@ApiOperation(value = "남자 모델 등록", notes = "남자 모델을 등록한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "브랜드 등록성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Integer insertMenModel(@Valid AdminModelDTO adminModelDTO,
								  CommonImageDTO commonImageDTO,
								  NewCommonDTO newCommonDTO,
								  HttpServletRequest request,
								  @RequestParam(name="imageFiles", required = false) MultipartFile[] fileName) throws Exception{

		searchCommon.giveAuth(request, newCommonDTO);

		Integer result = this.adminModelApiService.insertMenModel(adminModelDTO, commonImageDTO, fileName);

		return result;
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
	@ApiOperation(value = "남자 모델 상세 조회", notes = "남자 모델을 상세 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@GetMapping("/men/{idx}")
	public ConcurrentHashMap<String, Object> getMenModelEdit(@PathVariable("idx") Integer idx) throws Exception {
		ConcurrentHashMap<String, Object> modelMap;

		AdminModelDTO adminModelDTO = new AdminModelDTO();
		adminModelDTO.setIdx(idx);
		adminModelDTO.setCategoryCd("1");

		modelMap = this.adminModelApiService.getModelInfo(adminModelDTO);

		return modelMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : insertWomenModel
	 * 2. ClassName  : AdminModelApi.java
	 * 3. Comment    : 관리자 여자 모델 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 10. 06
	 * </pre>
	 *
	 * @param fileName
	 * @param adminModelDTO
	 * @param commonImageDTO
	 * @param newCommonDTO
	 * @param request
	 * @throws Exception
	 */
	@ApiOperation(value = "여자 모델 등록", notes = "여자 모델을 등록한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "브랜드 등록성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@PostMapping(value = "/women", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Integer insertWomenModel(@Valid AdminModelDTO adminModelDTO,
									CommonImageDTO commonImageDTO,
									NewCommonDTO newCommonDTO,
									HttpServletRequest request,
									@RequestParam(name="imageFiles", required = false) MultipartFile[] fileName) throws Exception {
		searchCommon.giveAuth(request, newCommonDTO);

		Integer result = this.adminModelApiService.insertWomenModel(adminModelDTO, commonImageDTO, fileName);

		return result;
	}

	/**
	 * <pre>
	 * 1. MethodName : getWomenModelEdit
	 * 2. ClassName  : AdminModelApi.java
	 * 3. Comment    : 관리자 여자 모델 상세
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param idx
	 * @throws Exception
	 */
	@ApiOperation(value = "여자 모델 상세 조회", notes = "여자 모델을 상세 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@GetMapping("/women/{idx}")
	public ConcurrentHashMap getWomenModelEdit(@PathVariable("idx") Integer idx) throws Exception {
		ConcurrentHashMap<String, Object> modelMap;

		AdminModelDTO adminModelDTO = new AdminModelDTO();
		adminModelDTO.setIdx(idx);
		adminModelDTO.setCategoryCd("2");

		modelMap = this.adminModelApiService.getModelInfo(adminModelDTO);

		return modelMap;
	}
}
