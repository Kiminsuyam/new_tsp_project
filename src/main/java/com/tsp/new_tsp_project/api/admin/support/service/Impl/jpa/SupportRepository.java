package com.tsp.new_tsp_project.api.admin.support.service.Impl.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tsp.new_tsp_project.api.admin.support.domain.dto.AdminSupportDTO;
import com.tsp.new_tsp_project.api.admin.support.domain.entity.AdminSupportEntity;
import com.tsp.new_tsp_project.api.admin.support.domain.entity.QAdminSupportEntity;
import com.tsp.new_tsp_project.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SupportRepository {

	private final EntityManager em;

	private String getSupportQuery(Map<String, Object> supportMap) {
		String query = "select m from AdminSupportEntity m where m.visible = :visible";

		if ("0".equals(StringUtil.getString(supportMap.get("searchType"), "0"))) {
			query += " and (m.supportName like :searchKeyword or m.supportMessage like :searchKeyword)";
		} else if ("1".equals(StringUtil.getString(supportMap.get("searchType"), "0"))) {
			query += " and m.supportName like :searchKeyword";
		} else {
			query += " and m.supportMessage like :searchKeyword";
		}
		return query;
	}

	/**
	 * <pre>
	 * 1. MethodName : findSupportModelCount
	 * 2. ClassName  : ProductionRepository.java
	 * 3. Comment    : 관리자 지원모델 리스트 갯수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 26.
	 * </pre>
	 *
	 * @param supportMap
	 * @throws Exception
	 */
	public Integer findSupportModelCount(Map<String, Object> supportMap) throws Exception {
		String query = getSupportQuery(supportMap);

		return em.createQuery(query, AdminSupportEntity.class)
				.setParameter("searchKeyword", "%" + StringUtil.getString(supportMap.get("searchKeyword"),"") + "%")
				.setParameter("visible", "Y")
				.getResultList().size();
	}

	/**
	 * <pre>
	 * 1. MethodName : findSupportModelList
	 * 2. ClassName  : ProductionRepository.java
	 * 3. Comment    : 관리자 지원모델 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 26.
	 * </pre>
	 *
	 * @param supportMap
	 * @throws Exception
	 */
	public List<AdminSupportDTO> findSupportModelList(Map<String, Object> supportMap) throws Exception {
		String query = getSupportQuery(supportMap);

		List<AdminSupportEntity> supportList = em.createQuery(query, AdminSupportEntity.class)
				.setParameter("searchKeyword", "%" + StringUtil.getString(supportMap.get("searchKeyword"),"") + "%")
				.setParameter("visible", "Y")
				.getResultList();

		List<AdminSupportDTO> supportDtoList = SupportMapper.INSTANCE.toDtoList(supportList);

		for(int i = 0; i < supportDtoList.size(); i++) {
			supportDtoList.get(i).setRnum(StringUtil.getInt(supportMap.get("startPage"),1)*(StringUtil.getInt(supportMap.get("size"),1))-(2-i));
		}

		return supportDtoList;
	}

	/**
	 * <pre>
	 * 1. MethodName : findOneSupportModel
	 * 2. ClassName  : ProductionRepository.java
	 * 3. Comment    : 관리자 지원모델 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 26.
	 * </pre>
	 *
	 * @param adminSupportEntity
	 * @throws Exception
	 */
	public Map<String, Object> findOneSupportModel(AdminSupportEntity adminSupportEntity) throws Exception {
		QAdminSupportEntity qAdminSupportEntity = QAdminSupportEntity.adminSupportEntity;

		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		//모델 상세 조회
		AdminSupportEntity findOneSupportModel = jpaQueryFactory.selectFrom(qAdminSupportEntity)
				.where(qAdminSupportEntity.idx.eq(adminSupportEntity.getIdx()))
				.fetchOne();

		Map<String, Object> supportMap = new HashMap<>();

		supportMap.put("supportModelInfo", SupportMapper.INSTANCE.toDto(findOneSupportModel));

		return supportMap;
	}
}
