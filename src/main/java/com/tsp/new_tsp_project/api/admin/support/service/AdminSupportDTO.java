package com.tsp.new_tsp_project.api.admin.support.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class AdminSupportDTO {

	@ApiModelProperty(value = "지원모델 IDX", required = true, hidden = true)
	private Integer idx;

	@ApiModelProperty(value = "지원모델 이름", required = true)
	private String supportName;

	@ApiModelProperty(value = "지원모델 Height", required = true)
	private Integer supportHeight;

	@ApiModelProperty(value = "지원모델 3Size", required = true)
	private String supportSize3;

	@ApiModelProperty(value = "지원모델 instagram")
	private String supportInstagram;

	@ApiModelProperty(value = "지원모델 휴대폰번호", required = true)
	private String supportPhone;

	@ApiModelProperty(value = "지원모델 내용", required = true)
	private String supportMessage;

}
