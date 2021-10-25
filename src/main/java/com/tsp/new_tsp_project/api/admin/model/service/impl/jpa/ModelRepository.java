package com.tsp.new_tsp_project.api.admin.model.service.impl.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.tsp.new_tsp_project.api.admin.model.domain.dto.AdminModelDTO;
import com.tsp.new_tsp_project.api.admin.model.domain.entity.AdminModelEntity;
import com.tsp.new_tsp_project.api.admin.model.domain.entity.QAdminModelEntity;
import com.tsp.new_tsp_project.api.common.domain.entity.CommonImageEntity;
import com.tsp.new_tsp_project.api.common.domain.entity.ModelCodeEntity;
import com.tsp.new_tsp_project.api.common.domain.entity.QCommonImageEntity;
import com.tsp.new_tsp_project.api.common.image.service.jpa.ImageRepository;
import com.tsp.new_tsp_project.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ModelRepository {

	private final ImageRepository imageRepository;
	private final EntityManager em;

	private String getModelQuery(Map<String, Object> modelMap) {
		String query = "select m from AdminModelEntity m join fetch m.newModelCodeJpaDTO where m.categoryCd = :categoryCd and m.visible = :visible and m.newModelCodeJpaDTO.cmmType = :cmmType";

		if ("0".equals(StringUtil.getString(modelMap.get("searchType"), "0"))) {
			query += " and (m.modelKorName like :searchKeyword or m.modelEngName like :searchKeyword or m.modelDescription like :searchKeyword)";
		} else if ("1".equals(StringUtil.getString(modelMap.get("searchType"), "0"))) {
			query += " and (m.modelKorName like :searchKeyword or m.modelEngName like :searchKeyword)";
		} else {
			query += " and (m.modelDescription like :searchKeyword)";
		}
		return query;
	}

	/**
	 * <pre>
	 * 1. MethodName : findModelsCount
	 * 2. ClassName  : ModelRepository.java
	 * 3. Comment    : 관리자 모델 리스트 갯수 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param modelMap
	 * @throws Exception
	 */
	public Integer findModelsCount(Map<String, Object> modelMap) throws Exception {
		String query = getModelQuery(modelMap);

		return em.createQuery(query, AdminModelEntity.class)
				.setParameter("categoryCd", StringUtil.getInt(modelMap.get("categoryCd"),0))
				.setParameter("searchKeyword", "%" + StringUtil.getString(modelMap.get("searchKeyword"),"") + "%")
				.setParameter("visible", "Y")
				.setParameter("cmmType","model")
				.getResultList().size();
	}

	/**
	 * <pre>
	 * 1. MethodName : findModelsList
	 * 2. ClassName  : ModelRepository.java
	 * 3. Comment    : 관리자 모델 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param modelMap
	 * @throws Exception
	 */
	public List<AdminModelDTO> findModelsList(Map<String, Object> modelMap) throws Exception{
		String query = getModelQuery(modelMap);

		List<AdminModelEntity> modelList = em.createQuery(query, AdminModelEntity.class)
				.setParameter("categoryCd", StringUtil.getInt(modelMap.get("categoryCd"),0))
				.setParameter("searchKeyword", "%" + StringUtil.getString(modelMap.get("searchKeyword"),"") + "%")
				.setParameter("visible", "Y")
				.setParameter("cmmType","model")
				.setFirstResult(StringUtil.getInt(modelMap.get("jpaStartPage"),0))
				.setMaxResults(StringUtil.getInt(modelMap.get("size"),0))
				.getResultList();

		for(int i = 0; i < modelList.size(); i++) {
			modelList.get(i).setRnum(StringUtil.getInt(modelMap.get("startPage"),1)*(StringUtil.getInt(modelMap.get("size"),1))-(2-i));
		}

		List<AdminModelDTO> modelDtoList = ModelMapper.INSTANCE.toDtoList(modelList);

		return modelDtoList;
	}

	/**
	 * <pre>
	 * 1. MethodName : modelCommonCode
	 * 2. ClassName  : ModelRepository.java
	 * 3. Comment    : 관리자 모델 공통 코드 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param modelCodeEntity
	 * @throws Exception
	 */
	public ConcurrentHashMap<String, Object> modelCommonCode(ModelCodeEntity modelCodeEntity) throws Exception {
		ConcurrentHashMap<String, Object> modelCommonMap = new ConcurrentHashMap<>();

		String query = "select m from ModelCodeEntity m where m.cmmType = :cmmType";

		List<ModelCodeEntity> codeEntityList = em.createQuery(query, ModelCodeEntity.class)
				.setParameter("cmmType", modelCodeEntity.getCmmType()).getResultList();

		modelCommonMap.put("codeEntityList", codeEntityList);

		return modelCommonMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : findOneModel
	 * 2. ClassName  : ModelRepository.java
	 * 3. Comment    : 관리자 모델 상세 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminModelEntity
	 * @throws Exception
	 */
	public ConcurrentHashMap<String, Object> findOneModel(AdminModelEntity adminModelEntity) throws Exception {
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

	/**
	 * <pre>
	 * 1. MethodName : insertModel
	 * 2. ClassName  : ModelRepository.java
	 * 3. Comment    : 관리자 모델 등록
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminModelEntity
	 * @param commonImageEntity
	 * @param files
	 * @throws Exception
	 */
	@Modifying
	@Transactional
	public Integer insertModel(AdminModelEntity adminModelEntity,
							   CommonImageEntity commonImageEntity,
							   MultipartFile[] files) throws Exception {

		Date date = new Date();
		adminModelEntity.builder().createTime(date).creator(1).build();
		em.persist(adminModelEntity);

		commonImageEntity.builder().typeName("model").typeIdx(adminModelEntity.getIdx()).build();

		imageRepository.uploadImageFile(commonImageEntity, files);

		return adminModelEntity.getIdx();
	}

	/**
	 * <pre>
	 * 1. MethodName : updateModel
	 * 2. ClassName  : ModelRepository.java
	 * 3. Comment    : 관리자 모델 수정
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminModelEntity
	 * @param commonImageEntity
	 * @param files
	 * @throws Exception
	 */
	@Modifying
	@Transactional
	public Integer updateModel(AdminModelEntity adminModelEntity, CommonImageEntity commonImageEntity,
							   MultipartFile[] files, ConcurrentHashMap<String, Object> modelMap) throws Exception {

		QAdminModelEntity qAdminModelEntity = QAdminModelEntity.adminModelEntity;

		JPAUpdateClause update = new JPAUpdateClause(em, qAdminModelEntity);

		Date currentTime = new Date ();

		adminModelEntity.builder().updateTime(currentTime).updater(1).build();

		update.set(qAdminModelEntity.modelKorName, adminModelEntity.getModelKorName())
				.set(qAdminModelEntity.categoryCd, adminModelEntity.getCategoryCd())
				.set(qAdminModelEntity.modelEngName, adminModelEntity.getModelEngName())
				.set(qAdminModelEntity.modelDescription, adminModelEntity.getModelDescription())
				.set(qAdminModelEntity.height, adminModelEntity.getHeight())
				.set(qAdminModelEntity.size3, adminModelEntity.getSize3())
				.set(qAdminModelEntity.shoes, adminModelEntity.getShoes())
				.set(qAdminModelEntity.categoryAge, adminModelEntity.getCategoryAge())
				.set(qAdminModelEntity.updateTime, adminModelEntity.getUpdateTime())
				.set(qAdminModelEntity.updater, 1)
				.where(qAdminModelEntity.idx.eq(adminModelEntity.getIdx())).execute();

		commonImageEntity.setTypeName("model");
		commonImageEntity.setTypeIdx(adminModelEntity.getIdx());

		modelMap.put("typeName", "model");

		if("Y".equals(imageRepository.updateMultipleFile(commonImageEntity, files, modelMap))) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * <pre>
	 * 1. MethodName : deleteModel
	 * 2. ClassName  : ModelRepository.java
	 * 3. Comment    : 관리자 모델 삭제
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param adminModelEntity
	 * @throws Exception
	 */
	public Integer deleteModel(AdminModelEntity adminModelEntity) throws Exception {
		QAdminModelEntity qAdminModelEntity = QAdminModelEntity.adminModelEntity;

		JPAUpdateClause update = new JPAUpdateClause(em, qAdminModelEntity);

		Date currentTime = new Date ();

		adminModelEntity.builder().updateTime(currentTime).updater(1).build();

		update.set(qAdminModelEntity.visible, "N")
				.set(qAdminModelEntity.updateTime, adminModelEntity.getUpdateTime())
				.set(qAdminModelEntity.updater, 1)
				.where(qAdminModelEntity.idx.eq(adminModelEntity.getIdx())).execute();

		return 1;
	}
}
