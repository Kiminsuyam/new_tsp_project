package com.tsp.new_tsp_project.api.admin.portfolio.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class AdminPortFolioDTO {

	@ApiModelProperty(required = true, value = "idx", hidden = true)
	Integer idx;

	@ApiModelProperty(required = true, value = "title")
	String title;

	@ApiModelProperty(required = true, value = "hashTag")
	String hashTag;

	@ApiModelProperty(required = true, value = "categoryCd")
	String categoryCd;

	@ApiModelProperty(required = true, value = "categoryNm")
	String categoryNm;

	@ApiModelProperty(required = true, value = "videoUrl")
	String videoUrl;

	@ApiModelProperty(required = true, value = "description")
	String description;
}
