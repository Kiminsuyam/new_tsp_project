package com.tsp.new_tsp_project.api.admin.user.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class AdminUserDTO {

	@ApiModelProperty(required = true, value = "user Seq", hidden = true)
	String userSeq;

	@ApiModelProperty(required = true, value = "user Id")
	String userId;

	@ApiModelProperty(required = true, value = "user Password")
	String password;

	@ApiModelProperty(required = true, value = "user Name", hidden = true)
	String userName;

	@ApiModelProperty(required = true, value = "user Type", hidden = true)
	String userTypeCd;

	@ApiModelProperty(required = false, value = "user Token", hidden = true)
	String userToken;
}
