package com.tsp.new_tsp_project.api.admin.production.service.impl.jpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.tsp.new_tsp_project.api.admin.model.service.impl.jpa.ModelImageMapper;
import com.tsp.new_tsp_project.api.admin.production.domain.dto.AdminProductionDTO;
import com.tsp.new_tsp_project.api.admin.production.domain.entity.AdminProductionEntity;
import com.tsp.new_tsp_project.api.common.domain.entity.CommonImageEntity;
import com.tsp.new_tsp_project.api.common.image.service.jpa.ImageRepository;
import com.tsp.new_tsp_project.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tsp.new_tsp_project.api.admin.production.domain.entity.QAdminProductionEntity.*;
import static com.tsp.new_tsp_project.api.common.domain.entity.QCommonImageEntity.*;

@Repository
@RequiredArgsConstructor
public class ProductionRepository {

	private final JPAQueryFactory queryFactory;
	private final EntityManager em;
	private final ImageRepository imageRepository;


	private BooleanExpression searchProduction(Map<String, Object> productionMap) {
		String searchType = StringUtil.getString(productionMap.get("searchType"),"");
		String searchKeyword = StringUtil.getString(productionMap.get("searchKeyword"),"");

		if ("0".equals(searchType)) {
			return adminProductionEntity.title.contains(searchKeyword)
					.or(adminProductionEntity.description.contains(searchKeyword));
		} else if ("1".equals(searchType)) {
			return adminProductionEntity.title.contains(searchKeyword);
		} else {
			return adminProductionEntity.description.contains(searchKeyword);
		}
	}

	/**
	 * <pre>
	 * 1. MethodName : findProductionCount
	 * 2. ClassName  : ProductionRepository.java
	 * 3. Comment    : ????????? ???????????? ????????? ?????? ??????
	 * 4. ?????????       : CHO
	 * 5. ?????????       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param productionMap
	 * @throws Exception
	 */
	public Long findProductionCount(Map<String, Object> productionMap) throws Exception {

		return queryFactory.selectFrom(adminProductionEntity)
				.where(searchProduction(productionMap))
				.fetchCount();
	}

	/**
	 * <pre>
	 * 1. MethodName : findProductionList
	 * 2. ClassName  : ProductionRepository.java
	 * 3. Comment    : ????????? ???????????? ????????? ??????
	 * 4. ?????????       : CHO
	 * 5. ?????????       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param productionMap
	 * @throws Exception
	 */
	public List<AdminProductionDTO> findProductionList(Map<String, Object> productionMap) throws Exception {

		List<AdminProductionEntity> productionList = queryFactory
				.selectFrom(adminProductionEntity)
				.where(searchProduction(productionMap))
				.orderBy(adminProductionEntity.idx.desc())
				.offset(StringUtil.getInt(productionMap.get("jpaStartPage"),0))
				.limit(StringUtil.getInt(productionMap.get("size"),0))
				.fetch();

		List<AdminProductionDTO> productionDtoList = ProductionMapper.INSTANCE.toDtoList(productionList);

		for(int i = 0; i < productionDtoList.size(); i++) {
			productionDtoList.get(i).setRnum(StringUtil.getInt(productionMap.get("startPage"),1)*(StringUtil.getInt(productionMap.get("size"),1))-(2-i));
		}

		return productionDtoList;

	}

	/**
	 * <pre>
	 * 1. MethodName : findOneProduction
	 * 2. ClassName  : ProductionRepository.java
	 * 3. Comment    : ????????? ???????????? ?????? ??????
	 * 4. ?????????       : CHO
	 * 5. ?????????       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param existAdminProductionEntity
	 * @throws Exception
	 */
	public ConcurrentHashMap<String, Object> findOneProduction(AdminProductionEntity existAdminProductionEntity) throws Exception {

		//?????? ?????? ??????
		AdminProductionEntity findOneProduction = queryFactory.selectFrom(adminProductionEntity)
				.where(adminProductionEntity.idx.eq(existAdminProductionEntity.getIdx()))
				.fetchOne();

		//??????????????? ????????? ??????
		List<CommonImageEntity> productionImageList = queryFactory.selectFrom(commonImageEntity)
				.where(commonImageEntity.typeIdx.eq(existAdminProductionEntity.getIdx()),
						commonImageEntity.visible.eq("Y"),
						commonImageEntity.typeName.eq("production")).fetch();

		ConcurrentHashMap<String, Object> productionMap = new ConcurrentHashMap<>();

		productionMap.put("productionInfo", ProductionMapper.INSTANCE.toDto(findOneProduction));
		productionMap.put("productionImageList", ModelImageMapper.INSTANCE.toDtoList(productionImageList));

		return productionMap;
	}

	/**
	 * <pre>
	 * 1. MethodName : insertProduction
	 * 2. ClassName  : ProductionRepository.java
	 * 3. Comment    : ????????? ???????????? ??????
	 * 4. ?????????       : CHO
	 * 5. ?????????       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param adminProductionEntity
	 * @param commonImageEntity
	 * @param files
	 * @throws Exception
	 */
	public Integer insertProduction(AdminProductionEntity adminProductionEntity,
									CommonImageEntity commonImageEntity,
									MultipartFile[] files) throws Exception {

		adminProductionEntity.builder().createTime(new Date()).creator(1).build();
		em.persist(adminProductionEntity);
		em.flush();
		em.clear();

		commonImageEntity.builder()
				.typeName("production")
				.typeIdx(adminProductionEntity.getIdx())
				.build();

		imageRepository.uploadImageFile(commonImageEntity, files);

		return adminProductionEntity.getIdx();
	}

	/**
	 * <pre>
	 * 1. MethodName : updateProduction
	 * 2. ClassName  : ProductionRepository.java
	 * 3. Comment    : ????????? ???????????? ??????
	 * 4. ?????????       : CHO
	 * 5. ?????????       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param existAdminProductionEntity
	 * @param commonImageEntity
	 * @param files
	 * @throws Exception
	 */
	public Integer updateProduction(AdminProductionEntity existAdminProductionEntity,
									CommonImageEntity commonImageEntity,
									MultipartFile[] files) throws Exception {

		JPAUpdateClause update = new JPAUpdateClause(em, adminProductionEntity);

		existAdminProductionEntity.builder().updateTime(new Date()).updater(1).build();

		update.set(adminProductionEntity.title, existAdminProductionEntity.getTitle())
				.set(adminProductionEntity.description, existAdminProductionEntity.getDescription())
				.set(adminProductionEntity.visible, "Y")
				.set(adminProductionEntity.updateTime, new Date())
				.set(adminProductionEntity.updater, 1)
				.where(adminProductionEntity.idx.eq(existAdminProductionEntity.getIdx())).execute();

		commonImageEntity.builder()
				.typeName("production")
				.typeIdx(existAdminProductionEntity.getIdx())
				.build();

		ConcurrentHashMap<String, Object> productionMap = new ConcurrentHashMap<>();
		productionMap.put("typeName", "portfolio");

		imageRepository.uploadImageFile(commonImageEntity, files);

		return 1;
	}

	/**
	 * <pre>
	 * 1. MethodName : deleteProduction
	 * 2. ClassName  : ProductionRepository.java
	 * 3. Comment    : ????????? ???????????? ??????
	 * 4. ?????????       : CHO
	 * 5. ?????????       : 2021. 09. 22.
	 * </pre>
	 *
	 * @param existAdminProductionEntity
	 * @throws Exception
	 */
	public Long deleteProduction(AdminProductionEntity existAdminProductionEntity) throws Exception {
		JPAUpdateClause update = new JPAUpdateClause(em, adminProductionEntity);

		Date currentTime = new Date();

		existAdminProductionEntity.builder().updateTime(currentTime).updater(1).build();

		return update.set(adminProductionEntity.title, existAdminProductionEntity.getTitle())
				.set(adminProductionEntity.visible, "N")
				.set(adminProductionEntity.updateTime, currentTime)
				.set(adminProductionEntity.updater, 1)
				.where(adminProductionEntity.idx.eq(existAdminProductionEntity.getIdx())).execute();
	}
}
