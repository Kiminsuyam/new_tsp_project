package com.tsp.new_tsp_project.api.admin.user.service.impl.jpa;

import com.querydsl.jpa.impl.JPAUpdateClause;
import com.tsp.new_tsp_project.api.admin.production.domain.entity.QAdminProductionEntity;
import com.tsp.new_tsp_project.api.admin.user.dto.AdminUserDTO;
import com.tsp.new_tsp_project.api.admin.user.entity.AdminUserEntity;
import com.tsp.new_tsp_project.api.admin.user.entity.QAdminUserEntity;
import com.tsp.new_tsp_project.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepository {

	private final EntityManager em;

	private String getUserQuery(Map<String, Object> userMap) {
		String query = "select m from AdminUserEntity m where m.visible = :visible";

		return query;
	}

	/**
	 * <pre>
	 * 1. MethodName : findUserList
	 * 2. ClassName  : UserRepository.java
	 * 3. Comment    : 관리자 유저 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param userMap
	 * @throws Exception
	 */
	public List<AdminUserDTO> findUserList(Map<String, Object> userMap) throws Exception {
		String query = getUserQuery(userMap);

		List<AdminUserEntity> userList = em.createQuery(query, AdminUserEntity.class)
				.setParameter("visible", "Y")
				.setFirstResult(StringUtil.getInt(userMap.get("jpaStartPage"),0))
				.setMaxResults(StringUtil.getInt(userMap.get("size"),0))
				.getResultList();

		List<AdminUserDTO> userDtoList = UserMapper.INSTANCE.toDtoList(userList);

		for(int i = 0; i < userDtoList.size(); i++) {
			userDtoList.get(i).setRnum(StringUtil.getInt(userMap.get("startPage"),1)*(StringUtil.getInt(userMap.get("size"),1))-(2-i));
		}

		return userDtoList;
	}

	/**
	 * <pre>
	 * 1. MethodName : adminLogin
	 * 2. ClassName  : UserRepository.java
	 * 3. Comment    : 관리자 로그인 처리
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminUserEntity
	 * @throws Exception
	 */
	public String adminLogin(AdminUserEntity adminUserEntity) throws Exception {
		String query = "select m from AdminUserEntity m where m.visible = :visible and m.userId = :userId";

		String password = em.createQuery(query, AdminUserEntity.class)
				.setParameter("visible", "Y")
				.setParameter("userId", adminUserEntity.getUserId())
				.getSingleResult().getPassword();

		return password;
	}

	/**
	 * <pre>
	 * 1. MethodName : insertUserToken
	 * 2. ClassName  : UserRepository.java
	 * 3. Comment    : 회원 로그인 후 토큰 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminUserEntity
	 * @throws Exception
	 */
	public Integer insertUserToken(AdminUserEntity adminUserEntity) throws Exception {
		QAdminUserEntity qAdminUserEntity = QAdminUserEntity.adminUserEntity;
		JPAUpdateClause update = new JPAUpdateClause(em, qAdminUserEntity);

		Date currentTime = new Date();

		update.set(qAdminUserEntity.userToken, adminUserEntity.getUserToken())
				.set(qAdminUserEntity.updater, 1)
				.set(qAdminUserEntity.updateTime, currentTime)
				.where(qAdminUserEntity.userId.eq(adminUserEntity.getUserId())).execute();

		return 1;
	}
}
