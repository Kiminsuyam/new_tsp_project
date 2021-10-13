package com.tsp.new_tsp_project.api.common.image.service;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class NewCommonJpaDTO {

	@Column(name = "creator", insertable = false)
	@ApiModelProperty(required = true, value = "등록자", hidden = true)
	private Integer creator;

	@Column(name = "updater", insertable = false)
	@ApiModelProperty(required = true, value = "수정자", hidden = true)
	private Integer updater;

	@Column(name = "create_time", insertable = false)
	@ApiModelProperty(required = true, value = "등록 일자", hidden = true)
	private String createTime;

	@Column(name = "update_time", insertable = false)
	@ApiModelProperty(required = true, value = "수정 일자", hidden = true)
	private String updateTime;
}
