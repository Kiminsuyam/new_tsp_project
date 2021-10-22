package com.tsp.new_tsp_project.api.admin.model.domain.entity;

import com.tsp.new_tsp_project.api.common.domain.entity.NewCodeEntity;
import com.tsp.new_tsp_project.api.common.domain.entity.NewCommonMappedClass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "tsp_model")
public class AdminModelEntity extends NewCommonMappedClass {

	@Transient
	private Integer rnum;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idx")
	private Integer idx;

	@Column(name = "category_cd")
	private Integer categoryCd;

	@Column(name = "category_age")
	private String categoryAge;

	@Column(name = "model_kor_name")
	@NotNull(message = "모델 국문 이름 입력은 필수입니다.")
	private String modelKorName;

	@Column(name = "model_eng_name")
	@NotNull(message = "모델 영문 이름 입력은 필수입니다.")
	private String modelEngName;

	@Column(name = "height")
	private String height;

	@Column(name = "size3")
	private String size3;

	@Column(name = "shoes")
	private String shoes;

	@Column(name = "model_description")
	@Lob
	private String modelDescription;

	@Column(name = "visible")
	private String visible;

	@ManyToOne(fetch = LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "category_cd", insertable = false, updatable = false)
	private NewCodeEntity newCodeJpaDTO;
}

