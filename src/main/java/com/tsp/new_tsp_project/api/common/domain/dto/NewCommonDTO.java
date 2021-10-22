package com.tsp.new_tsp_project.api.common.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ApiModel
public class NewCommonDTO {

	@ApiModelProperty(required = true, value = "등록자", hidden = true)
	private Integer creator;

	@ApiModelProperty(required = true, value = "수정자", hidden = true)
	private Integer updater;

	@ApiModelProperty(required = true, value = "등록자 이름", hidden = true)
	private String adminName;

	@ApiModelProperty(required = true, value = "등록 일자", hidden = true)
	private Date createTime;

	@ApiModelProperty(required = true, value = "수정 일자", hidden = true)
	private Date updateTime;
}
