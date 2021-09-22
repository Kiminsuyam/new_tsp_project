package com.tsp.new_tsp_project.api.admin.production.service;

import com.tsp.new_tsp_project.api.common.NewCommonDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class AdminProductionDTO extends NewCommonDTO {

	@ApiModelProperty(required = true, value = "idx", hidden = true)
	Integer idx;

	@ApiModelProperty(required = true, value = "title")
	String title;

	@ApiModelProperty(required = true, value = "description")
	String description;

	@ApiModelProperty(required = true, value = "visible")
	String visible;
}
