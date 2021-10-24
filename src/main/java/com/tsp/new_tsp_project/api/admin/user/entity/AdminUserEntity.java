package com.tsp.new_tsp_project.api.admin.user.entity;

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
@Table(name = "tsp_admin")
public class AdminUserEntity extends NewCommonMappedClass {

	@Transient
	private Integer rnum;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idx")
	Integer idx;

	@Column(name = "user_id")
	String userId;

	@Column(name = "password")
	String password;

	@Column(name = "name")
	String name;

	@Column(name = "email")
	String email;

	@Column(name = "visible")
	String visible;

	@Column(name = "user_token")
	String userToken;
}
