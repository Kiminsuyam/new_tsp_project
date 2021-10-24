package com.tsp.new_tsp_project.api.admin.user.service.jpa;

import com.tsp.new_tsp_project.api.admin.user.dto.AdminUserDTO;
import com.tsp.new_tsp_project.api.admin.user.entity.AdminUserEntity;
import com.tsp.new_tsp_project.api.admin.user.service.impl.jpa.UserRepository;
import com.tsp.new_tsp_project.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminUserJpaService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	/**
	 * <pre>
	 * 1. MethodName : findUserList
	 * 2. ClassName  : AdminUserJpaService.java
	 * 3. Comment    : 관리자 유저 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param userMap
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<AdminUserDTO> findUserList(Map<String, Object> userMap) throws Exception {
		return userRepository.findUserList(userMap);
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
	 * @param adminUserEntity
	 * @param request
	 * @return result
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public String adminLogin(@Validated AdminUserEntity adminUserEntity, HttpServletRequest request, BindingResult bindingResult) throws Exception {

		if(bindingResult.hasErrors()) {
			return "redirect:/login";
		}

		final String db_pw = StringUtils.nullStrToStr(this.userRepository.adminLogin(adminUserEntity));

		String result = "";

		if (passwordEncoder.matches(adminUserEntity.getPassword(), db_pw)) {
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
	 * @param adminUserEntity
	 * @return result
	 * @throws Exception
	 */
	@Modifying
	@Transactional
	public Integer insertUserToken(AdminUserEntity adminUserEntity) throws Exception {
		return this.userRepository.insertUserToken(adminUserEntity);
	}
}
