package com.tsp.new_tsp_project.api.common.image.service;

import com.tsp.new_tsp_project.api.admin.model.service.AdminModelDTO;
import com.tsp.new_tsp_project.api.common.image.CommonImageDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface ImageService {

    /**
     * <pre>
     * 1. MethodName : uploadImageFile
     * 2. ClassName  : ImageService.java
     * 3. Comment    : 이미지 파일 업로드
     * 4. 작성자       : CHO
     * 5. 작성일       : 2021. 06. 02.
     * </pre>
     *
     * @param commonImageDTO
     * @param files
     * @return
     * @throws Exception
     */
    String uploadImageFile(CommonImageDTO commonImageDTO,
                           MultipartFile[] files) throws Exception;
}
