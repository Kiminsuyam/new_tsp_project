package com.tsp.new_tsp_project.api.admin.model.service;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tsp.new_tsp_project.api.common.image.service.NewCommonJpaDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tsp_model")
public class AdminModelJpaDTO extends NewCommonJpaDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idx")
	Integer idx;

	@Column(name = "category_cd")
	Integer categoryCd;

	@Column(name = "category_nm")
	String categoryNm;

	@Column(name = "category_age")
	String categoryAge;

	@Column(name = "model_kor_name")
	String modelKorName;

	@Column(name = "model_eng_name")
	String modelEngName;

	@Column(name = "height")
	private String height;

	@Column(name = "size3")
	private String size3;

	@Column(name = "shoes")
	private String shoes;

	@Column(name = "model_description")
	String modelDescription;

	@Column(name = "visible")
	String visible;

}

