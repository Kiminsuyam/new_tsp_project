package com.tsp.new_tsp_project.api.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class NewCommonDTO {

	@ApiModelProperty(required = true, value = "등록자", hidden=true)
	private Integer creator;

	@ApiModelProperty(required = true, value = "수정자", hidden=true)
	private Integer updater;
}
