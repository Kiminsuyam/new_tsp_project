package com.tsp.new_tsp_project.api.admin.production.domain.entity;

import com.tsp.new_tsp_project.api.common.domain.entity.NewCommonMappedClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "tsp_production")
public class AdminProductionEntity extends NewCommonMappedClass {

	@Transient
	private Integer rnum;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idx")
	private Integer idx;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	@Lob
	private String description;

	@Column(name = "visible")
	private String visible;

}
