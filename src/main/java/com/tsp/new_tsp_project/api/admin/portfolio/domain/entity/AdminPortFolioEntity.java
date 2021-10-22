package com.tsp.new_tsp_project.api.admin.portfolio.domain.entity;

import com.tsp.new_tsp_project.api.common.domain.entity.NewCodeEntity;
import com.tsp.new_tsp_project.api.common.domain.entity.NewCommonMappedClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "tsp_portfolio")
public class AdminPortFolioEntity extends NewCommonMappedClass {

	@Transient
	private Integer rnum;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idx")
	private Integer idx;

	@Column(name = "category_cd")
	private Integer categoryCd;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	@Lob
	private String description;

	@Column(name = "hash_tag")
	private String hashTag;

	@Column(name = "video_url")
	private String videoUrl;

	@Column(name = "visible")
	private String visible;

	@ManyToOne(fetch = LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "category_cd", insertable = false, updatable = false)
	private NewCodeEntity newCodeJpaDTO;
}
