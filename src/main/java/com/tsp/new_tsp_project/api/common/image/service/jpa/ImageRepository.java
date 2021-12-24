package com.tsp.new_tsp_project.api.common.image.service.jpa;

import com.querydsl.jpa.impl.JPAUpdateClause;
import com.tsp.new_tsp_project.api.common.domain.entity.CommonImageEntity;
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

import static com.tsp.new_tsp_project.api.common.domain.entity.CommonImageEntity.builder;
import static com.tsp.new_tsp_project.api.common.domain.entity.QCommonImageEntity.commonImageEntity;

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

		String rtnStr = sdfCurrent.format(ts.getTime());

		return rtnStr;
	}

	public String updateMultipleFile(CommonImageEntity existCommonImageEntity,
									 MultipartFile[] files, ConcurrentHashMap<String, Object> commandMap) throws Exception {

		// 파일 확장자
		String ext;
		// 파일명
		String fileId;
		// 파일 Mask
		String fileMask;
		// 파일 크기
		long fileSize;

		String [] arrayState = (String []) commandMap.get("arrayState");
		String [] arrayIdx = (String []) commandMap.get("arrayIdx");

		File dir = new File(uploadPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		int fileCnt = 0;

		JPAUpdateClause update = new JPAUpdateClause(em, commonImageEntity);

		try {
			for(int i = 0; i < arrayState.length; i++) {
				if("U".equals(arrayState[i])) {
					if(files[fileCnt] != null) {
						ext = files[fileCnt].getOriginalFilename()
								.substring(files[fileCnt].getOriginalFilename().lastIndexOf(".") + 1)
								.toLowerCase();

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
							builder().fileNum(0).visible("N").imageType("main").build();

							Long result = update.set(commonImageEntity.visible, "N")
											.where(commonImageEntity.typeIdx.eq(existCommonImageEntity.getIdx()),
													commonImageEntity.typeName.eq(StringUtil.getString(commandMap.get("typeName"), "")),
													commonImageEntity.imageType.eq("main")).execute();

							if (result > 0) {
								em.detach(commonImageEntity);
							}
						} else {
							String query = "select COALESCE(max(m.fileNum),0)+1 from CommonImageEntity m where m.typeIdx = :type_idx and m.visible = :visible";

							Integer size = StringUtil.getInt(em.createQuery(query, Integer.class)
									.setParameter("type_idx", existCommonImageEntity.getTypeIdx())
									.setParameter("visible", "Y").getSingleResult(), 0);

							builder()
									.fileNum(size)
									.imageType("sub" + size)
									.build();
						}

						builder().fileName(files[fileCnt].getOriginalFilename())
										.fileSize(fileSize)
										.fileMask(fileMask)
										.filePath(uploadPath + fileMask)
										.build();

						em.createNativeQuery("INSERT INTO tsp_image (type_idx, type_name, file_num, file_name, file_size, file_mask, file_path, image_type, visible)" +
										"VALUES(?,?,?,?,?,?,?,?,?)")
								.setParameter(1, existCommonImageEntity.getTypeIdx())
								.setParameter(2, existCommonImageEntity.getTypeName())
								.setParameter(3, existCommonImageEntity.getFileNum())
								.setParameter(4, existCommonImageEntity.getFileName())
								.setParameter(5, existCommonImageEntity.getFileSize())
								.setParameter(6, existCommonImageEntity.getFileMask())
								.setParameter(7, existCommonImageEntity.getFilePath())
								.setParameter(8, existCommonImageEntity.getImageType())
								.setParameter(9, "Y").executeUpdate();

						fileCnt++;
					}
				} else if("D".equals(arrayState[i]) || "H".equals(arrayState[i])) {
					existCommonImageEntity.setIdx(StringUtil.getInt(arrayIdx[i],0));
					Long result = update.set(commonImageEntity.visible, "N")
									.where(commonImageEntity.idx.eq(existCommonImageEntity.getIdx()),
											commonImageEntity.typeName.eq(existCommonImageEntity.getTypeName()))
									.execute();

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
		if (!dir.exists()) {
			dir.mkdirs();
		}

		if(files != null) {

			for (MultipartFile file : files) {
				try {
					ext = file.getOriginalFilename()
							.substring(file.getOriginalFilename().lastIndexOf(".")+1)
							.toLowerCase();

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
						builder().imageType("main").build();
					} else {
						builder().imageType("sub" + mainCnt);
					}

					String filePath = uploadPath + fileMask;
					file.transferTo(new File(filePath));

					Runtime.getRuntime().exec("chmod -R 755 " + filePath);

					builder()
								.fileNum(mainCnt)
								.fileName(file.getOriginalFilename())
								.fileSize(fileSize)
								.fileMask(fileMask)
								.visible("Y")
								.filePath(uploadPath + fileMask)
								.build();

					em.persist(commonImageEntity);
					em.flush();
					em.clear();
					mainCnt++;

				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception();
				}
			}
		}

	}
}
