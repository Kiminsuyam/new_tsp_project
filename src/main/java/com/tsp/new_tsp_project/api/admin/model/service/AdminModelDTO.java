package com.tsp.new_tsp_project.api.admin.model.service;

import com.tsp.new_tsp_project.api.common.NewCommonDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel
public class AdminModelDTO extends NewCommonDTO {

	@ApiModelProperty(required = true, value = "idx", hidden = true)
	Integer idx;

	@ApiModelProperty(required = true, value = "category code", hidden = true)
	String categoryCd;

	@ApiModelProperty(required = true, value = "category name", hidden = true)
	String categoryNm;

	@NotNull(message = "모델 이름 입력은 필수입니다.")
	@ApiModelProperty(required = true, value = "men Kor Name")
	String modelKorName;

	@NotNull(message = "모델 영문 이름 입력은 필수입니다.")
	@ApiModelProperty(required = true, value = "men Eng Name")
	String modelEngName;

	@NotNull(message = "모델 상세 내용 입력은 필수입니다.")
	@ApiModelProperty(required = true, value = "model Description")
	String modelDescription;

	@NotNull(message = "모델 키 입력은 필수입니다.")
	@ApiModelProperty(required = true, value = "model height")
	Integer height;

	@NotNull(message = "모델 사이즈 입력은 필수입니다.")
	@ApiModelProperty(required = true, value = "model 3size")
	String size3;

	@ApiModelProperty(required = true, value = "model shoes")
	Integer shoes;

	@ApiModelProperty(required = true, value = "visible", hidden = true)
	String visible;
}
