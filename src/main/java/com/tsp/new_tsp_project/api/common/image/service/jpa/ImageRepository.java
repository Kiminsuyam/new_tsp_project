package com.tsp.new_tsp_project.api.common.image.service.jpa;

import com.querydsl.jpa.impl.JPAInsertClause;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.tsp.new_tsp_project.api.common.domain.entity.CommonImageEntity;
import com.tsp.new_tsp_project.api.common.domain.entity.QCommonImageEntity;
import com.tsp.new_tsp_project.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ImageRepository {

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

	public String updateMultipleFile(CommonImageEntity commonImageEntity,
									 MultipartFile[] files, ConcurrentHashMap<String, Object> commandMap) throws Exception {

		// 파일 확장자
		String ext = "";
		// 파일명
		String fileId = "";
		// 파일 Mask
		String fileMask = "";
		// 파일 크기
		long fileSize = 0;

		String [] arrayState = (String []) commandMap.get("arrayState");
		String [] arrayIdx = (String []) commandMap.get("arrayIdx");

		File dir = new File(uploadPath);
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		int fileCnt = 0;

		QCommonImageEntity qCommonImageEntity = QCommonImageEntity.commonImageEntity;
		JPAUpdateClause update = new JPAUpdateClause(em, qCommonImageEntity);
		JPAInsertClause insert = new JPAInsertClause(em, qCommonImageEntity);

		try {
			for(int i = 0; i < arrayState.length; i++) {
				if("U".equals(arrayState[i])) {
					if(files[fileCnt] != null) {
						ext = files[fileCnt].getOriginalFilename().substring(files[fileCnt].getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
						fileId = currentDate();
						fileMask = fileId + '.' + ext;
						fileSize = files[fileCnt].getSize();

						if (!new File(uploadPath).exists()) {
							try {
								new File(uploadPath).mkdir();
							} catch (Exception e) {
								e.getStackTrace();
							}
						}

						String filePath = uploadPath + fileMask;
						files[fileCnt].transferTo(new File(filePath));

						Runtime.getRuntime().exec("chmod -R 755 " + filePath);

						if (i == 0) {
							commonImageEntity.setFileNum(0);
							commonImageEntity.setVisible("N");
							commonImageEntity.setImageType("main");// 파일Mask

							Long result = update.set(qCommonImageEntity.visible, "N")
									.where(qCommonImageEntity.typeIdx.eq(commonImageEntity.getIdx()),
											qCommonImageEntity.typeName.eq(StringUtil.getString(commandMap.get("typeName"), "")),
											qCommonImageEntity.imageType.eq("main")).execute();

							if (result > 0) {
								em.detach(commonImageEntity);
							}
						} else {
							String query = "select COALESCE(max(m.fileNum),0)+1 from CommonImageEntity m where m.typeIdx = :type_idx and m.visible = :visible";

							Integer size = StringUtil.getInt(em.createQuery(query, Integer.class)
									.setParameter("type_idx", commonImageEntity.getTypeIdx())
									.setParameter("visible", "Y").getSingleResult(), 0);

							commonImageEntity.setFileNum(size);
							commonImageEntity.setImageType("sub" + size);// 파일Mask
						}

						commonImageEntity.setFileName(files[fileCnt].getOriginalFilename());                   // 파일명
						commonImageEntity.setFileSize(fileSize);  // 파일Size
						commonImageEntity.setFileMask(fileMask);
						commonImageEntity.setFilePath(uploadPath + fileMask);


						em.createNativeQuery("INSERT INTO tsp_image (type_idx, type_name, file_num, file_name, file_size, file_mask, file_path, image_type, visible)" +
										"VALUES(?,?,?,?,?,?,?,?,?)")
								.setParameter(1, commonImageEntity.getTypeIdx())
								.setParameter(2, commonImageEntity.getTypeName())
								.setParameter(3, commonImageEntity.getFileNum())
								.setParameter(4, commonImageEntity.getFileName())
								.setParameter(5, commonImageEntity.getFileSize())
								.setParameter(6, commonImageEntity.getFileMask())
								.setParameter(7, commonImageEntity.getFilePath())
								.setParameter(8, commonImageEntity.getImageType())
								.setParameter(9, "Y").executeUpdate();

//						insert.columns(qCommonImageEntity.typeIdx,qCommonImageEntity.typeName, qCommonImageEntity.fileNum, qCommonImageEntity.fileName,
//										qCommonImageEntity.fileSize, qCommonImageEntity.fileMask, qCommonImageEntity.filePath, qCommonImageEntity.imageType,
//										qCommonImageEntity.visible).values(commonImageEntity.getTypeIdx(),commonImageEntity.getTypeName(), commonImageEntity.getFileNum(),
//										commonImageEntity.getFileName(),commonImageEntity.getFileSize(),commonImageEntity.getFileMask(),commonImageEntity.getFilePath(),
//										commonImageEntity.getImageType(), "Y").execute();


//						em.persist(commonImageEntity);

						fileCnt++;
					}
				} else if("D".equals(arrayState[i]) || "H".equals(arrayState[i])) {
					commonImageEntity.setIdx(StringUtil.getInt(arrayIdx[i],0));
					Integer result = em.createQuery("update CommonImageEntity m set m.visible = : visible where m.idx = : idx and m.typeName = : typeName")
							.setParameter("visible", "N")
							.setParameter("idx", commonImageEntity.getIdx())
							.setParameter("typeName", StringUtil.getString(commandMap.get("typeName"),"")).executeUpdate();

					if(result > 0) {
						em.detach(commonImageEntity);
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
//			Integer result = em.createQuery("update CommonImageEntity m set m.visible = : visible where m.typeIdx = : typeIdx and m.typeName = : typeName")
//					.setParameter("visible", "N")
//					.setParameter("typeIdx", commonImageEntity.getTypeIdx())
//					.setParameter("typeName", "model").executeUpdate();
//
//			if(result > 0) {
//				em.detach(commonImageEntity);
//			}

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
