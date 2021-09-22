package com.tsp.new_tsp_project.api.admin.model.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class AdminModelDTO {

	@ApiModelProperty(required = true, value = "idx", hidden = true)
	Integer idx;

	@ApiModelProperty(required = true, value = "category code", hidden = true)
	String categoryCd;

	@ApiModelProperty(required = true, value = "category name", hidden = true)
	String categoryNm;

	@ApiModelProperty(required = true, value = "men Name")
	String modelName;

	@ApiModelProperty(required = true, value = "model Description")
	String modelDescription;

	@ApiModelProperty(required = true, value = "visible", hidden = true)
	String visible;
}
