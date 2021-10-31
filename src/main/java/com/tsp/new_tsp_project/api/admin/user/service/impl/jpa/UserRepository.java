package com.tsp.new_tsp_project.api.admin.user.service.impl.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.tsp.new_tsp_project.api.admin.user.dto.AdminUserDTO;
import com.tsp.new_tsp_project.api.admin.user.entity.AdminUserEntity;
import com.tsp.new_tsp_project.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tsp.new_tsp_project.api.admin.user.entity.QAdminUserEntity.*;

@Repository
@RequiredArgsConstructor
public class UserRepository {

	private final JPAQueryFactory queryFactory;
	private final EntityManager em;

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

		List<AdminUserEntity> userList = queryFactory.selectFrom(adminUserEntity)
				.where(adminUserEntity.visible.eq("Y"))
				.orderBy(adminUserEntity.idx.desc())
				.offset(StringUtil.getInt(userMap.get("jpaStartPage"),0))
				.limit(StringUtil.getInt(userMap.get("size"),0))
				.fetch();

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
	 * @param existAdminUserEntity
	 * @throws Exception
	 */
	public Map<String, Object> adminLogin(AdminUserEntity existAdminUserEntity) throws Exception {

		Map<String, Object> userMap = new HashMap<>();

		try {
			AdminUserEntity NewAdminUserEntity = queryFactory.selectFrom(adminUserEntity)
					.where(adminUserEntity.visible.eq("Y"), adminUserEntity.userId.eq(existAdminUserEntity.getUserId()))
					.fetchOne();

			if(NewAdminUserEntity == null) {
				return null;
			}

			userMap.put("userId", NewAdminUserEntity.getUserId());
			userMap.put("password", NewAdminUserEntity.getPassword());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userMap;

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
	 * @param existAdminUserEntity
	 * @throws Exception
	 */
	public Integer insertUserToken(AdminUserEntity existAdminUserEntity) throws Exception {
		JPAUpdateClause update = new JPAUpdateClause(em, adminUserEntity);

		update.set(adminUserEntity.userToken, existAdminUserEntity.getUserToken())
				.set(adminUserEntity.updater, 1)
				.set(adminUserEntity.updateTime, new Date())
				.where(adminUserEntity.userId.eq(existAdminUserEntity.getUserId())).execute();

		return existAdminUserEntity.getIdx();
	}

	/**
	 * <pre>
	 * 1. MethodName : insertAdminUser
	 * 2. ClassName  : UserRepository.java
	 * 3. Comment    : 관리자 회원가입 처리
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminUserEntity
	 * @throws Exception
	 */
	public Integer insertAdminUser(AdminUserEntity adminUserEntity) throws Exception {

		//회원 등록
		em.persist(adminUserEntity);
		em.flush();
		em.clear();

		//회원 등록된 IDX
		AdminUserEntity newAdminUserEntity = em.find(AdminUserEntity.class, adminUserEntity.getIdx());
		Integer newIdx = newAdminUserEntity.getIdx();

		em.flush();
		em.close();

		return newIdx;
	}
}
