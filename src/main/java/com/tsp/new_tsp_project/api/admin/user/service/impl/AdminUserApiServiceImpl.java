package com.tsp.new_tsp_project.api.admin.user.service.impl;

import com.tsp.new_tsp_project.api.admin.user.service.AdminUserApiService;
import com.tsp.new_tsp_project.api.admin.user.service.AdminUserDTO;
import com.tsp.new_tsp_project.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service("AdminUserApiService")
@RequiredArgsConstructor
public class AdminUserApiServiceImpl implements AdminUserApiService {

	private final AdminUserMapper adminUserMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public List<AdminUserDTO> getUserList(Map<String, Object> commandMap) {
		return adminUserMapper.getUserList(commandMap);
	}

	/**
	 * <pre>
	 * 1. MethodName : adminLogin
	 * 2. ClassName  : AdminUserApiServiceImpl.java
	 * 3. Comment    : 회원 로그인 처리
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminUserDTO
	 * @param request
	 * @return result
	 * @throws Exception
	 */
	public String adminLogin(@Validated AdminUserDTO adminUserDTO, HttpServletRequest request, BindingResult bindingResult) throws Exception {

		if(bindingResult.hasErrors()) {
			return "redirect:/login";
		}

		final String db_pw = StringUtils.nullStrToStr(this.adminUserMapper.adminLogin(adminUserDTO));

		String result = "";

		if (passwordEncoder.matches(adminUserDTO.getPassword(), db_pw)) {
			result = "Y";
		} else {
			result = "N";
		}
		return result;
	}

	/**
	 * <pre>
	 * 1. MethodName : insertUserToken
	 * 2. ClassName  : AdminUserApiServiceImpl.java
	 * 3. Comment    : 회원 로그인 후 토큰 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminUserDTO
	 * @return result
	 * @throws Exception
	 */
	public Integer insertUserToken(AdminUserDTO adminUserDTO) throws Exception {
		return this.adminUserMapper.insertUserToken(adminUserDTO);
	}
}
