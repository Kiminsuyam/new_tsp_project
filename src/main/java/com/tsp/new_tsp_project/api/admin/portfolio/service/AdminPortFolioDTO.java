package com.tsp.new_tsp_project.api.admin.portfolio.service;

import com.tsp.new_tsp_project.api.common.NewCommonDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class AdminPortFolioDTO extends NewCommonDTO {

	@ApiModelProperty(required = true, value = "rnum", hidden = true)
	Integer rnum;

	@ApiModelProperty(required = true, value = "idx", hidden = true)
	Integer idx;

	@NotNull(message = "제목은 필수입니다.")
	@ApiModelProperty(required = true, value = "title")
	String title;

	@ApiModelProperty(required = true, value = "hashTag")
	String hashTag;

	@NotNull(message = "카테고리 선택은 필수입니다.")
	@ApiModelProperty(required = true, value = "categoryCd")
	String categoryCd;

	@ApiModelProperty(required = true, value = "categoryNm")
	String categoryNm;

	@ApiModelProperty(required = true, value = "videoUrl")
	String videoUrl;

	@NotNull(message = "상세 내용 입력은 필수입니다.")
	@ApiModelProperty(required = true, value = "description")
	String description;

	@ApiModelProperty(required = true, value = "visible")
	String visible;
}
