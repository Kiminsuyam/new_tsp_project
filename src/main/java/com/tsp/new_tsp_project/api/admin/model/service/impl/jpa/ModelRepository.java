package com.tsp.new_tsp_project.api.admin.model.service.impl.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.tsp.new_tsp_project.api.admin.model.domain.dto.AdminModelDTO;
import com.tsp.new_tsp_project.api.admin.model.domain.entity.AdminModelEntity;
import com.tsp.new_tsp_project.api.admin.model.service.QAdminModelEntity;
import com.tsp.new_tsp_project.api.common.domain.entity.CommonImageEntity;
import com.tsp.new_tsp_project.api.common.image.service.QCommonImageEntity;
import com.tsp.new_tsp_project.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityManager;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ModelRepository {

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

	private String getModelQuery(Map<String, Object> modelMap) {
		String query = "select m from AdminModelEntity m join fetch m.newCodeJpaDTO where m.categoryCd = :categoryCd and m.visible = :visible and m.newCodeJpaDTO.cmmType = :cmmType";

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
			log.info("===rnum={}", i);
			log.info("===newRnum={}",(StringUtil.getInt(modelMap.get("jpaStartPage"),1)+1)*i);
			AdminModelDTO.builder().rnum((StringUtil.getInt(modelMap.get("jpaStartPage"),1)+1)*i).build();
		}

		List<AdminModelDTO> modelDtoList = ModelMapper.INSTANCE.toDtoList(modelList);

		return modelDtoList;
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
	 * 5. 작성일       : 2021. 10. 06
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

		uploadImageFile(commonImageEntity, files);

		return adminModelEntity.getIdx();
	}

	/**
	 * <pre>
	 * 1. MethodName : updateModel
	 * 2. ClassName  : ModelRepository.java
	 * 3. Comment    : 관리자 모델 수정
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 10. 06
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

		updateMultipleFile(commonImageEntity, files, modelMap);

		return 1;
	}

	public String updateMultipleFile(CommonImageEntity commonImageEntity,
									 MultipartFile[] files, ConcurrentHashMap<String, Object> modelMap) throws Exception {

		// 파일 확장자
		String ext = "";
		// 파일명
		String fileId = "";
		// 파일 Mask
		String fileMask = "";
		// 파일 크기
		long fileSize = 0;

		String [] arrayState = (String []) modelMap.get("arrayState");
		String [] arrayIdx = (String []) modelMap.get("arrayIdx");

		File dir = new File(uploadPath);
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		int fileCnt = 0;

		QCommonImageEntity qCommonImageEntity = QCommonImageEntity.commonImageEntity;
		JPAUpdateClause update = new JPAUpdateClause(em, qCommonImageEntity);

		try {
			for(int i = 0; i < arrayState.length; i++) {
				if("U".equals(arrayState[i])) {
					if(files[fileCnt] != null) {
						ext = files[fileCnt].getOriginalFilename().substring(files[fileCnt].getOriginalFilename().lastIndexOf(".")+1).toLowerCase();
						fileId = currentDate();
						fileMask = fileId + '.' + ext;
						fileSize = files[fileCnt].getSize();

						if(!new File(uploadPath).exists()) {
							try {
								new File(uploadPath).mkdir();
							}catch(Exception e) {
								e.getStackTrace();
							}
						}

						String filePath = uploadPath + fileMask;
						files[fileCnt].transferTo(new File(filePath));

						Runtime.getRuntime().exec("chmod -R 755 " + filePath);

						if(i == 0) {
							commonImageEntity.setFileNum(0);
							commonImageEntity.setVisible("N");
							commonImageEntity.setImageType("main");// 파일Mask

							Long result = update.set(qCommonImageEntity.visible, "N")
											.where(qCommonImageEntity.typeIdx.eq(commonImageEntity.getIdx()),
													qCommonImageEntity.typeName.eq("model")).execute();

							if(result > 0) {
								em.detach(commonImageEntity);
							}
						} else {
							String query = "select COALESCE(max(m.fileNum),0)+1 from CommonImageEntity m where m.typeIdx = :type_idx and m.visible = :visible";

							Integer size = StringUtil.getInt(em.createQuery(query, Integer.class)
									.setParameter("type_idx", commonImageEntity.getTypeIdx())
									.setParameter("visible", "Y").getSingleResult(),0);

							commonImageEntity.setFileNum(size);
							commonImageEntity.setImageType("sub"+size);// 파일Mask
						}

						commonImageEntity.setFileName(files[fileCnt].getOriginalFilename());                   // 파일명
						commonImageEntity.setFileSize(fileSize);  // 파일Size
						commonImageEntity.setFileMask(fileMask);
						commonImageEntity.setFilePath(uploadPath + fileMask);

//						em.createNativeQuery("INSERT INTO tsp_image (type_idx, type_name, file_num, file_name, file_size, file_mask, image_type, visible)" +
//										"VALUES(?,?,?,?,?,?,?,?)")
//								.setParameter(1, commonImageEntity.getTypeIdx())
//								.setParameter(2, commonImageEntity.getTypeName())
//								.setParameter(3, commonImageEntity.getFileNum())
//								.setParameter(4, commonImageEntity.getFileName())
//								.setParameter(5, commonImageEntity.getFileSize())
//								.setParameter(6, commonImageEntity.getFileMask())
//								.setParameter(7, commonImageEntity.getImageType())
//								.setParameter(8, "Y").executeUpdate();

						em.persist(commonImageEntity);

						fileCnt++;
					} else if("D".equals(arrayState[i]) || "H".equals(arrayState[i])) {
						commonImageEntity.setIdx(StringUtil.getInt(arrayIdx[i],0));
						Integer result = em.createQuery("update CommonImageEntity m set m.visible = : visible where m.idx = : idx and m.typeName = : typeName")
								.setParameter("visible", "N")
								.setParameter("idx", commonImageEntity.getIdx())
								.setParameter("typeName", "model").executeUpdate();

						if(result > 0) {
							em.detach(commonImageEntity);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return "Y";
	}

	public void uploadImageFile(CommonImageEntity commonImageEntity,
								MultipartFile[] files) throws Exception {

		// 파일 확장자
		String ext = "";
		// 파일명
		String fileId = "";
		// 파일 Mask
		String fileMask = "";
		// 파일 크기
		long fileSize = 0;

		int mainCnt = 0;

		File dir = new File(uploadPath);
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		if(files != null) {
			Integer result = em.createQuery("update CommonImageEntity m set m.visible = : visible where m.typeIdx = : typeIdx and m.typeName = : typeName")
					.setParameter("visible", "N")
					.setParameter("typeIdx", commonImageEntity.getTypeIdx())
					.setParameter("typeName", "model").executeUpdate();

			if(result > 0) {
				em.detach(commonImageEntity);
			}

			for (MultipartFile file : files) {
				try {
					ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();
					fileId = currentDate();
					fileMask = fileId + '.' + ext;
					fileSize = file.getSize();

					if(!new File(uploadPath).exists()) {
						try {
							new File(uploadPath).mkdir();
						}catch(Exception e) {
							e.getStackTrace();
						}
					}

					if(mainCnt == 0) {
						commonImageEntity.setImageType("main");
					} else {
						commonImageEntity.setImageType("sub"+mainCnt);
					}

					String filePath = uploadPath + fileMask;
					file.transferTo(new File(filePath));

					Runtime.getRuntime().exec("chmod -R 755 " + filePath);

					commonImageEntity.setFileNum(mainCnt);
					commonImageEntity.setFileName(file.getOriginalFilename());                   // 파일명
					commonImageEntity.setFileSize(fileSize);  // 파일Size
					commonImageEntity.setFileMask(fileMask);                                        // 파일Mask
					commonImageEntity.setVisible("Y");
					commonImageEntity.setFilePath(uploadPath + fileMask);

					em.persist(commonImageEntity);
					mainCnt++;

				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception();
				}
			}
		}

	}
}
