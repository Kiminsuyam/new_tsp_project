package com.tsp.new_tsp_project.api.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class NewCommonDTO {

	@ApiModelProperty(required = true, value = "등록자", hidden = true)
	private Integer creator;

	@ApiModelProperty(required = true, value = "수정자", hidden = true)
	private Integer updater;

	@ApiModelProperty(required = true, value = "등록자 이름", hidden = true)
	private String adminName;

	@ApiModelProperty(required = true, value = "등록 일자", hidden = true)
	private String createTime;

	@ApiModelProperty(required = true, value = "수정 일자", hidden = true)
	private String updateTime;
}
