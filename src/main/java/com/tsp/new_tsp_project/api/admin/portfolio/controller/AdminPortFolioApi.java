package com.tsp.new_tsp_project.api.admin.portfolio.controller;

import com.tsp.new_tsp_project.api.admin.portfolio.service.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.admin.portfolio.service.AdminPortFolioApiService;
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

import javax.servlet.http.HttpServletRequest;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequestMapping(value = "/api/portfolio")
@RestController
@RequiredArgsConstructor
@Api(tags = "포트폴리오 관련 API")
public class AdminPortFolioApi {

	private final AdminPortFolioApiService adminPortFolioApiService;
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
	public ConcurrentHashMap getPortFolioList(Page page, String searchType, String searchKeyword) throws Exception {
		ConcurrentHashMap<String, Object> portFolioMap = new ConcurrentHashMap<>();

		ConcurrentHashMap<String, Object> searchMap = searchCommon.searchCommon(page, searchKeyword, searchType);

		Integer portFolioCnt = this.adminPortFolioApiService.getPortFolioCnt(searchMap);

		List<AdminPortFolioDTO> portFolioList = new ArrayList<>();

		if(portFolioCnt > 0) {
			portFolioList = this.adminPortFolioApiService.getPortFolioList(searchMap);
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
	@GetMapping(value = "/{idx}")
	public ConcurrentHashMap getPortFolioInfo(@PathVariable("idx") Integer idx) throws Exception {
		ConcurrentHashMap<String, Object> portFolioMap;

		AdminPortFolioDTO adminPortFolioDTO = AdminPortFolioDTO.builder().idx(idx).build();

		portFolioMap = this.adminPortFolioApiService.getPortFolioInfo(adminPortFolioDTO);

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
	 * @param adminPortFolioDTO
	 * @param commonImageDTO
	 * @param files
	 * @throws Exception
	 */
	@ApiOperation(value = "포트폴리오 등록", notes = "포트폴리오를 등록한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String insertPortFolio(AdminPortFolioDTO adminPortFolioDTO,
								  CommonImageDTO commonImageDTO,
								  @RequestParam(value = "imageFiles", required = false) List<MultipartFile> files) throws Exception {
		String result = "N";

		if(this.adminPortFolioApiService.insertPortFolio(adminPortFolioDTO, commonImageDTO, files) > 0) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}

	/**
	 * <pre>
	 * 1. MethodName : updatePortFolio
	 * 2. ClassName  : AdminPortFolio.java
	 * 3. Comment    : 관리자 포트폴리오 수정
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminPortFolioDTO
	 * @param commonImageDTO
	 * @param files
	 * @throws Exception
	 */
	@ApiOperation(value = "포트폴리오 수정", notes = "포트폴리오를 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@PostMapping(value = "/{idx}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String updatePortFolio(AdminPortFolioDTO adminPortFolioDTO,
								  CommonImageDTO commonImageDTO,
								  @RequestParam(value = "imageFiles", required = false) List<MultipartFile> files) throws Exception {
		String result = "N";

		if(this.adminPortFolioApiService.updatePortFolio(adminPortFolioDTO, commonImageDTO, files) > 0) {
			result = "Y";
		} else {
			result = "N";
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. MethodName : deletePortFolio
	 * 2. ClassName  : AdminPortFolio.java
	 * 3. Comment    : 관리자 포트폴리오 삭제
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 28.
	 * </pre>
	 *
	 * @param idx
	 * @throws Exception
	 */
	@ApiOperation(value = "포트폴리오 삭제", notes = "포트폴리오를 삭제한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = Map.class),
			@ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.class),
			@ApiResponse(code = 500, message = "서버 에러", response = ServerError.class)
	})
	@DeleteMapping(value = "/{idx}")
	public String deletePortFolio(@PathVariable(value = "idx") Integer idx) throws Exception {
		AdminPortFolioDTO adminPortFolioDTO = AdminPortFolioDTO.builder().idx(idx).build();

		String result = "N";

		if(this.adminPortFolioApiService.deletePortFolio(adminPortFolioDTO) > 0) {
			result = "Y";
		} else {
			result = "N";
		}

		return result;
	}

}
