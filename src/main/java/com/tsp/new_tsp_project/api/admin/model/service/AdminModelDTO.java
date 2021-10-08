package com.tsp.new_tsp_project.api.admin.model.service;

import com.tsp.new_tsp_project.api.common.NewCommonDTO;
import com.tsp.new_tsp_project.api.common.image.CommonImageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ApiModel
public class AdminModelDTO extends NewCommonDTO {

	@ApiModelProperty(required = true, value = "idx", hidden = true)
	Integer idx;

	@ApiModelProperty(required = true, value = "model idx", hidden = true)
	Integer modelIdx;

	@ApiModelProperty(required = true, value = "category code", hidden = true)
	String categoryCd;

	@ApiModelProperty(required = true, value = "category name", hidden = true)
	String categoryNm;

	@ApiModelProperty(required = true, value = "category age", hidden = true)
	String categoryAge;

	@NotNull(message = "모델 국문 이름 입력은 필수입니다.")
	@ApiModelProperty(required = true, value = "men Kor Name")
	String modelKorName;

	@NotNull(message = "모델 영문 이름 입력은 필수입니다.")
	@ApiModelProperty(required = true, value = "men Eng Name")
	String modelEngName;

	@NotNull(message = "모델 상세 내용 입력은 필수입니다.")
	@ApiModelProperty(required = true, value = "model Description")
	String modelDescription;

	@ApiModelProperty(required = true, value = "model height")
	@NotNull(message = "모델 키 입력은 필수입니다.")
	@Pattern(regexp="\\\\d{1,3}", message = "숫자만 입력 가능합니다.")
//	@Length(min=1, max=4, message = "1자 이상 4자미만으로 작성해야 합니다.")
	String height;

	@NotNull(message = "모델 사이즈 입력은 필수입니다.")
//	@Pattern(regexp="/^([0-9]{2})$/-?([0-9]{2})$/-?([0-9]{2})$/", message = "**-**-** 형식으로 입력바랍니다.")
	@ApiModelProperty(required = true, value = "model 3size")
	String size3;

	@ApiModelProperty(required = true, value = "model shoes")
	@NotNull(message = "모델 신발 사이즈 입력은 필수입니다.")
//	@Pattern(regexp="[0-9]{1~3}", message = "숫자만 입력 가능합니다.")
//	@Length(min=1, max=4, message = "1자 이상 4자미만으로 작성해야 합니다.")
	String shoes;

	@ApiModelProperty(required = true, value = "visible")
	String visible;

	private List<CommonImageDTO> commonImageDTOList;
}
