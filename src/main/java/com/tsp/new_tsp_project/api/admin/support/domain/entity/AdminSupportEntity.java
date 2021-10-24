package com.tsp.new_tsp_project.api.admin.support.domain.entity;

import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "tsp_support")
public class AdminSupportEntity {

	@Transient
	private Integer rnum;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idx")
	private Integer idx;

	@Column(name = "support_name")
	private String supportName;

	@Column(name = "support_height")
	private Integer supportHeight;

	@Column(name = "support_size3")
	private String supportSize3;

	@Column(name = "support_instagram")
	private String supportInstagram;

	@Column(name = "support_phone")
	private String supportPhone;

	@Column(name = "support_message")
	@Lob
	private String supportMessage;

	@Column(name = "visible")
	private String visible;

	@Column(name = "support_time")
	private Date supportTime;
}
