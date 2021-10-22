package com.tsp.new_tsp_project.api.admin.portfolio.service.Impl.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tsp.new_tsp_project.api.admin.model.domain.entity.AdminModelEntity;
import com.tsp.new_tsp_project.api.admin.model.domain.entity.QAdminModelEntity;
import com.tsp.new_tsp_project.api.admin.model.service.impl.jpa.ModelImageMapper;
import com.tsp.new_tsp_project.api.admin.model.service.impl.jpa.ModelMapper;
import com.tsp.new_tsp_project.api.admin.portfolio.domain.dto.AdminPortFolioDTO;
import com.tsp.new_tsp_project.api.admin.portfolio.domain.entity.AdminPortFolioEntity;
import com.tsp.new_tsp_project.api.common.domain.entity.CommonImageEntity;
import com.tsp.new_tsp_project.api.common.domain.entity.QCommonImageEntity;
import com.tsp.new_tsp_project.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PortFolioRepository {

	/**
	 * 업로드 경로
	 **/
	@Value("${image.uploadPath}")
	private String uploadPath;

	private final EntityManager em;

	/**
	 * <pre>
	 * 1. MethodName : currentDate
	 * 2. ClassName  : ImageServiceImpl.java
	 * 3. Comment    : 현재 날짜 구하기
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 06. 02.
	 * </pre>
	 *
	 * @return
	 */
	public String currentDate() {
		// 현재 날짜 구하기
		String pattern = "MMddHHmmssSSS";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		String rtnStr = sdfCurrent.format(Long.valueOf(ts.getTime()));

		return rtnStr;
	}

	private String getPortFolioQuery(Map<String, Object> portFolioMap) {
		String query = "select m from AdminPortFolioEntity m join fetch m.newCodeJpaDTO where m.visible = :visible and m.newCodeJpaDTO.cmmType = :cmmType";

		if ("0".equals(StringUtil.getString(portFolioMap.get("searchType"), "0"))) {
			query += " and (m.title like :searchKeyword or m.description like :searchKeyword)";
		} else if ("1".equals(StringUtil.getString(portFolioMap.get("searchType"), "0"))) {
			query += " and m.title like :searchKeyword";
		} else {
			query += " and m.description like :searchKeyword";
		}
		return query;
	}

	/**
	 * <pre>
	 * 1. MethodName : findPortFolioCount
	 * 2. ClassName  : PortFolioRepository.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 갯수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param portFolioMap
	 * @throws Exception
	 */
	public Integer findPortFolioCount(Map<String, Object> portFolioMap) throws Exception {
		String query = getPortFolioQuery(portFolioMap);

		return em.createQuery(query, AdminPortFolioEntity.class)
				.setParameter("searchKeyword", "%" + StringUtil.getString(portFolioMap.get("searchKeyword"),"") + "%")
				.setParameter("visible", "Y")
				.setParameter("cmmType","portfolio")
				.getResultList().size();
	}

	/**
	 * <pre>
	 * 1. MethodName : findPortFolioList
	 * 2. ClassName  : PortFolioRepository.java
	 * 3. Comment    : 관리자 포트폴리오 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param portFolioMap
	 * @throws Exception
	 */
	public List<AdminPortFolioDTO> findPortFolioList(Map<String, Object> portFolioMap) throws Exception {
		String query = getPortFolioQuery(portFolioMap);

		List<AdminPortFolioEntity> portFolioList = em.createQuery(query, AdminPortFolioEntity.class)
				.setParameter("searchKeyword", "%" + StringUtil.getString(portFolioMap.get("searchKeyword"),"") + "%")
				.setParameter("visible", "Y")
				.setParameter("cmmType", "portfolio")
				.setFirstResult(StringUtil.getInt(portFolioMap.get("jpaStartPage"),0))
				.setMaxResults(StringUtil.getInt(portFolioMap.get("size"),0))
				.getResultList();

		List<AdminPortFolioDTO> portFolioDtoList = PortFolioMapper.INSTANCE.toDtoList(portFolioList);

		for(int i = 0; i < portFolioDtoList.size(); i++) {
			portFolioDtoList.get(i).setRnum(StringUtil.getInt(portFolioMap.get("startPage"),1)*(StringUtil.getInt(portFolioMap.get("size"),1))-(2-i));
		}

		return portFolioDtoList;
	}

	/**
	 * <pre>
	 * 1. MethodName : findOnePortFolio
	 * 2. ClassName  : PortFolioRepository.java
	 * 3. Comment    : 관리자 포트폴리오 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminModelEntity
	 * @throws Exception
	 */
	public ConcurrentHashMap<String, Object> findOnePortFolio(AdminModelEntity adminModelEntity) throws Exception {
		QAdminModelEntity qAdminModelEntity = QAdminModelEntity.adminModelEntity;
		QCommonImageEntity qCommonImageEntity = QCommonImageEntity.commonImageEntity;

		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		//모델 상세 조회
		AdminModelEntity findModel = jpaQueryFactory.selectFrom(qAdminModelEntity)
				.where(qAdminModelEntity.idx.eq(adminModelEntity.getIdx()))
				.fetchOne();

		//모델 이미지 조회
		List<CommonImageEntity> modelImageList = jpaQueryFactory.selectFrom(qCommonImageEntity)
				.where(qCommonImageEntity.typeIdx.eq(adminModelEntity.getIdx()),
						qCommonImageEntity.visible.eq("Y"),
						qCommonImageEntity.typeName.eq("model")).fetch();

		ConcurrentHashMap<String, Object> modelMap = new ConcurrentHashMap<>();

		modelMap.put("modelInfo", ModelMapper.INSTANCE.toDto(findModel));
		modelMap.put("modelImageList", ModelImageMapper.INSTANCE.toDtoList(modelImageList));

		return modelMap;

	}
}
