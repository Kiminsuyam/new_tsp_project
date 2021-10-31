package com.tsp.new_tsp_project.api.common.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "tsp_cmm_code")
@Getter
@Setter
public class CommonCodeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idx")
	Integer idx;

	@Column(name = "category_cd")
	Integer categoryCd;

	@Column(name = "category_nm")
	String categoryNm;

	@Column(name = "visible")
	String visible;

	@Column(name = "cmm_type")
	String cmmType;
}
