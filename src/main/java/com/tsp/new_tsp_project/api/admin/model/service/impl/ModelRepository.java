package com.tsp.new_tsp_project.api.admin.model.service.impl;

import com.tsp.new_tsp_project.api.admin.model.service.AdminModelJpaDTO;
import com.tsp.new_tsp_project.api.common.image.CommonImageDTO;
import com.tsp.new_tsp_project.api.common.image.service.CommonImageJpaDTO;
import com.tsp.new_tsp_project.api.common.image.service.ImageService;
import com.tsp.new_tsp_project.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
public class ModelRepository {

	/**
	 * 업로드 경로
	 **/
	@Value("${image.uploadPath}")
	private String uploadPath;

	@PersistenceContext
	private EntityManager em;

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

	public Integer findModelsCount(Map<String, Object> modelMap) throws Exception {
		return em.createQuery("select m from AdminModelJpaDTO m", AdminModelJpaDTO.class)
				.getResultList().size();
	}

	public List<AdminModelJpaDTO> findAll(Map<String, Object> modelMap) throws Exception{
		return em.createQuery("select m from AdminModelJpaDTO m", AdminModelJpaDTO.class)
				.setFirstResult(StringUtil.getInt(modelMap.get("startPage"),0))
				.setMaxResults(StringUtil.getInt(modelMap.get("size"),0))
				.getResultList();
	}

	public Map<String, Object> findOneModel(AdminModelJpaDTO adminModelJpaDTO) throws Exception {
		TypedQuery<AdminModelJpaDTO> query = em.createQuery("select m from AdminModelJpaDTO m where m.idx = :idx", AdminModelJpaDTO.class);
		query.setParameter("idx", adminModelJpaDTO.getIdx());

		Map<String, Object> modelMap = new HashMap<>();

		modelMap.put("modelInfo", query.getSingleResult());

		return modelMap;

	}

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
			em.createQuery("update CommonImageJpaDTO m set m.visible = : visible where m.typeIdx = : typeIdx and m.typeName = : typeName")
					.setParameter("visible", "Y")
					.setParameter("typeIdx", adminModelJpaDTO.getIdx())
					.setParameter("typeName", "model").executeUpdate();


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
					commonImageJpaDTO.setFileNum(mainCnt);
					commonImageJpaDTO.setFileName(file.getOriginalFilename());                   // 파일명
					commonImageJpaDTO.setFileSize(fileSize);  // 파일Size
					commonImageJpaDTO.setFileMask(fileMask);                                        // 파일Mask
					commonImageJpaDTO.setFilePath(uploadPath + fileMask);

					em.persist(commonImageJpaDTO);
					mainCnt++;
					// 이미지 정보 insert
//					if(imageMapper.addImageFile(commonImageDTO)>0) {
//						mainCnt++;
//					}

				} catch (Exception e) {
					throw new Exception();
				}
			}
		}
		return adminModelJpaDTO.getIdx();
	}
}
