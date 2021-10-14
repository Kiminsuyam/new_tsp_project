package com.tsp.new_tsp_project.api.admin.model.service.impl;

import com.querydsl.jpa.impl.JPAInsertClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.tsp.new_tsp_project.api.admin.model.service.AdminModelJpaDTO;
import com.tsp.new_tsp_project.api.admin.model.service.QAdminModelJpaDTO;
import com.tsp.new_tsp_project.api.common.image.service.CommonImageJpaDTO;
import com.tsp.new_tsp_project.api.common.image.service.QCommonImageJpaDTO;
import com.tsp.new_tsp_project.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ModelRepository {

	/**
	 * 업로드 경로
	 **/
	@Value("${image.uploadPath}")
	private String uploadPath;

	@PersistenceContext
	private EntityManager em;

	private JPAQueryFactory jpaQueryFactory;

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
		String rtnStr = "";
		String pattern = "MMddHHmmssSSS";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		rtnStr = sdfCurrent.format(Long.valueOf(ts.getTime()));

		return rtnStr;
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
		return em.createQuery("select m from AdminModelJpaDTO m", AdminModelJpaDTO.class)
				.getResultList().size();
	}

	/**
	 * <pre>
	 * 1. MethodName : findAll
	 * 2. ClassName  : ModelRepository.java
	 * 3. Comment    : 관리자 모델 리스트 조회
	 * 4. 작성자       : CHO
	 * 5. 작성일       : 2021. 09. 08.
	 * </pre>
	 *
	 * @param modelMap
	 * @throws Exception
	 */
	public List<AdminModelJpaDTO> findAll(Map<String, Object> modelMap) throws Exception{
//		return em.createQuery("select m from AdminModelJpaDTO m", AdminModelJpaDTO.class)
//				.setFirstResult(StringUtil.getInt(modelMap.get("startPage"),0))
//				.setMaxResults(StringUtil.getInt(modelMap.get("size"),0))
//				.getResultList();

		JPAQueryFactory query = new JPAQueryFactory(em);
		QAdminModelJpaDTO m = QAdminModelJpaDTO.adminModelJpaDTO;

		return query.selectFrom(m)
				.offset(StringUtil.getInt(modelMap.get("startPage"),0))
				.limit(StringUtil.getInt(modelMap.get("size"),0)).fetch();

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
	 * @param adminModelJpaDTO
	 * @throws Exception
	 */
	public Map<String, Object> findOneModel(AdminModelJpaDTO adminModelJpaDTO) throws Exception {
//		TypedQuery<AdminModelJpaDTO> query = em.createQuery("select m from AdminModelJpaDTO m where m.idx = :idx", AdminModelJpaDTO.class);

		JPAQueryFactory query = new JPAQueryFactory(em);
		QAdminModelJpaDTO m = QAdminModelJpaDTO.adminModelJpaDTO;

		Map<String, Object> modelMap = new HashMap<>();

		modelMap.put("modelInfo", query.selectFrom(m).where(m.idx.eq(adminModelJpaDTO.getIdx())).fetch());

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
	 * @param adminModelJpaDTO
	 * @param commonImageJpaDTO
	 * @param files
	 * @throws Exception
	 */
	@Modifying
	@Transactional
	public Integer insertModel(AdminModelJpaDTO adminModelJpaDTO,
							   CommonImageJpaDTO commonImageJpaDTO,
							   MultipartFile[] files) throws Exception {
		if("man".equals(adminModelJpaDTO.getCategoryCd())) {
			adminModelJpaDTO.setCategoryCd(1);
			adminModelJpaDTO.setCategoryNm("man");
		} else  if("woman".equals(adminModelJpaDTO.getCategoryCd())) {
			adminModelJpaDTO.setCategoryCd(2);
			adminModelJpaDTO.setCategoryNm("woman");
		} else {
			adminModelJpaDTO.setCategoryCd(3);
			adminModelJpaDTO.setCategoryNm("senior");
		}

		em.persist(adminModelJpaDTO);

		commonImageJpaDTO.setTypeName("model");
		commonImageJpaDTO.setTypeIdx(adminModelJpaDTO.getIdx());

		uploadImageFile(commonImageJpaDTO, files);

		return adminModelJpaDTO.getIdx();
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
	 * @param adminModelJpaDTO
	 * @param commonImageJpaDTO
	 * @param files
	 * @throws Exception
	 */
	@Modifying
	@Transactional
	public Integer updateModel(AdminModelJpaDTO adminModelJpaDTO, CommonImageJpaDTO commonImageJpaDTO, MultipartFile[] files) throws Exception {
//		if("1".equals(StringUtil.getString(adminModelJpaDTO.getCategoryCd(),""))) {
//			adminModelJpaDTO.setCategoryCd(1);
//			adminModelJpaDTO.setCategoryNm("man");
//		} else  if("2".equals(StringUtil.getString(adminModelJpaDTO.getCategoryCd(),""))) {
//			adminModelJpaDTO.setCategoryCd(2);
//			adminModelJpaDTO.setCategoryNm("woman");
//		} else {
//			adminModelJpaDTO.setCategoryCd(3);
//			adminModelJpaDTO.setCategoryNm("senior");
//		}

//		em.createQuery("update AdminModelJpaDTO m set m.modelKorName = : modelKorName, m.modelEngName = : modelEngName," +
//						"m.modelDescription = : modelDescription, m.height = : height, m.shoes = : shoes, m.categoryAge = : categoryAge " +
//						"where m.idx = : idx")
//				.setParameter("modelKorName", adminModelJpaDTO.getModelKorName())
//				.setParameter("modelEngName", adminModelJpaDTO.getModelEngName())
//				.setParameter("modelDescription", adminModelJpaDTO.getModelDescription())
//				.setParameter("height", adminModelJpaDTO.getHeight())
//				.setParameter("shoes", adminModelJpaDTO.getShoes())
//				.setParameter("categoryAge", adminModelJpaDTO.getCategoryAge())
//				.setParameter("idx", adminModelJpaDTO.getIdx());

		QAdminModelJpaDTO qAdminModelJpaDTO = QAdminModelJpaDTO.adminModelJpaDTO;

		JPAUpdateClause update = new JPAUpdateClause(em, qAdminModelJpaDTO);

		log.info("modelKorName={}", adminModelJpaDTO.getModelKorName());
		log.info("modelEngName={}", adminModelJpaDTO.getModelEngName());
		log.info("modelDescription={}", adminModelJpaDTO.getModelDescription());
		log.info("height={}", adminModelJpaDTO.getHeight());
		log.info("shoes={}", adminModelJpaDTO.getShoes());
		log.info("modelKorName={}", adminModelJpaDTO.getCategoryAge());
		log.info("modelKorName={}", adminModelJpaDTO.getIdx());
//
		update.set(qAdminModelJpaDTO.modelKorName, adminModelJpaDTO.getModelKorName())
				.set(qAdminModelJpaDTO.modelEngName, adminModelJpaDTO.getModelEngName())
				.set(qAdminModelJpaDTO.modelDescription, adminModelJpaDTO.getModelDescription())
				.set(qAdminModelJpaDTO.height, adminModelJpaDTO.getHeight())
				.set(qAdminModelJpaDTO.size3, adminModelJpaDTO.getSize3())
				.set(qAdminModelJpaDTO.shoes, adminModelJpaDTO.getShoes())
				.set(qAdminModelJpaDTO.categoryAge, adminModelJpaDTO.getCategoryAge())
						.where(qAdminModelJpaDTO.idx.eq(adminModelJpaDTO.getIdx())).execute();



		commonImageJpaDTO.setTypeName("model");
		commonImageJpaDTO.setTypeIdx(adminModelJpaDTO.getIdx());

		uploadImageFile(commonImageJpaDTO, files);

		return adminModelJpaDTO.getIdx();
	}

	public void uploadImageFile(CommonImageJpaDTO commonImageJpaDTO,
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
			Integer result = em.createQuery("update CommonImageJpaDTO m set m.visible = : visible where m.typeIdx = : typeIdx and m.typeName = : typeName")
					.setParameter("visible", "N")
					.setParameter("typeIdx", commonImageJpaDTO.getTypeIdx())
					.setParameter("typeName", "model").executeUpdate();

			if(result > 0) {
				em.detach(commonImageJpaDTO);
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
						commonImageJpaDTO.setImageType("main");
					} else {
						commonImageJpaDTO.setImageType("sub"+mainCnt);
					}

					String filePath = uploadPath + fileMask;
					file.transferTo(new File(filePath));

					Runtime.getRuntime().exec("chmod -R 755 " + filePath);

					log.info("fileName={}", file.getOriginalFilename());
					log.info("fileSize={}", fileSize);
					log.info("fileMask={}", fileMask);
					log.info("filePath={}", uploadPath+fileMask);
					log.info("modelIdx={}", commonImageJpaDTO.getTypeIdx());
					log.info("typeName={}", commonImageJpaDTO.getTypeName());
					commonImageJpaDTO.setFileNum(mainCnt);
					commonImageJpaDTO.setFileName(file.getOriginalFilename());                   // 파일명
					commonImageJpaDTO.setFileSize(fileSize);  // 파일Size
					commonImageJpaDTO.setFileMask(fileMask);                                        // 파일Mask
					commonImageJpaDTO.setVisible("Y");
					commonImageJpaDTO.setFilePath(uploadPath + fileMask);

					QCommonImageJpaDTO qCommonImageJpaDTO = QCommonImageJpaDTO.commonImageJpaDTO;
					JPAInsertClause jpaInsertClause = new JPAInsertClause(em, qCommonImageJpaDTO);

					jpaInsertClause.set(qCommonImageJpaDTO.typeIdx, commonImageJpaDTO.getTypeIdx())
									.set(qCommonImageJpaDTO.typeName, commonImageJpaDTO.getTypeName())
									.set(qCommonImageJpaDTO.fileNum, commonImageJpaDTO.getFileNum())
									.set(qCommonImageJpaDTO.fileName, commonImageJpaDTO.getFileName())
									.set(qCommonImageJpaDTO.fileSize, commonImageJpaDTO.getFileSize())
									.set(qCommonImageJpaDTO.fileMask, commonImageJpaDTO.getFileMask())
									.set(qCommonImageJpaDTO.imageType, commonImageJpaDTO.getImageType())
									.set(qCommonImageJpaDTO.visible, commonImageJpaDTO.getVisible()).execute();
//					em.persist(commonImageJpaDTO);
					mainCnt++;
					// 이미지 정보 insert
//					if(imageMapper.addImageFile(commonImageDTO)>0) {
//						mainCnt++;
//					}

				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception();
				}
			}
		}

	}
}
