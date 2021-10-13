package com.tsp.new_tsp_project.api.common.image.service;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class NewCommonJpaDTO {

	@ApiModelProperty(required = true, value = "등록자", hidden = true)
	private Integer creator;

	@ApiModelProperty(required = true, value = "수정자", hidden = true)
	private Integer updater;

	@ApiModelProperty(required = true, value = "등록 일자", hidden = true)
	private LocalDateTime createTime;

	@ApiModelProperty(required = true, value = "수정 일자", hidden = true)
	private LocalDateTime updateTime;
}
