package com.tsp.new_tsp_project.api.common.image.service.impl;

import com.tsp.new_tsp_project.api.admin.model.service.AdminModelDTO;
import com.tsp.new_tsp_project.api.common.image.CommonImageDTO;
import com.tsp.new_tsp_project.api.common.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Slf4j
@Service("ImageService")
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageMapper imageMapper;

    /**
     * 업로드 경로
     **/
    @Value("${image.uploadPath}")
    private String uploadPath;

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
     * 1. MethodName : uploadImageFile
     * 2. ClassName  : ImageServiceImpl.java
     * 3. Comment    : 이미지 파일 등록 & 업로드
     * 4. 작성자       : CHO
     * 5. 작성일       : 2021. 06. 02.
     * </pre>
     *
	 * @param commonImageDTO
     * @param files
     * @return
     * @throws Exception
     */
    public String uploadImageFile(CommonImageDTO commonImageDTO,
                                  MultipartFile[] files,
                                  String flag) throws Exception {

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
            if("update".equals(flag)) {
                imageMapper.deleteImageFile(commonImageDTO);
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

                    String filePath = uploadPath + fileMask;
                    file.transferTo(new File(filePath));

                    Runtime.getRuntime().exec("chmod -R 755 " + filePath);

                    log.info("fileName={}", file.getOriginalFilename());
                    log.info("fileSize={}", fileSize);
                    log.info("fileMask={}", fileMask);
                    log.info("filePath={}", uploadPath+fileMask);
                    log.info("modelIdx={}", commonImageDTO.getTypeIdx());
                    commonImageDTO.setFileNum(mainCnt);
                    commonImageDTO.setFileName(file.getOriginalFilename());                   // 파일명
                    commonImageDTO.setFileSize(fileSize);  // 파일Size
                    commonImageDTO.setFileMask(fileMask);                                        // 파일Mask
                    commonImageDTO.setFilePath(uploadPath + fileMask);

                    // 이미지 정보 insert
                    if(imageMapper.addImageFile(commonImageDTO)>0) {
                        mainCnt++;
                    }

                } catch (Exception e) {
                    throw new Exception();
                }
            }
        }

        return "Y";
    }
}
