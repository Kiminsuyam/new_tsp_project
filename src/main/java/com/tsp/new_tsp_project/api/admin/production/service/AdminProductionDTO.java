package com.tsp.new_tsp_project.api.admin.production.service;

import com.tsp.new_tsp_project.api.common.NewCommonDTO;
import com.tsp.new_tsp_project.api.common.image.CommonImageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class AdminProductionDTO extends NewCommonDTO {

	@ApiModelProperty(required = true, value = "idx", hidden = true)
	Integer idx;

	@NotNull(message = "제목 입력은 필수입니다.")
	@ApiModelProperty(required = true, value = "title")
	String title;

	@NotNull(message = "상세 내용 입력은 필수입니다.")
	@ApiModelProperty(required = true, value = "description")
	String description;

	@ApiModelProperty(required = true, value = "visible")
	String visible;

	private List<CommonImageDTO> commonImageDTOList;
}
