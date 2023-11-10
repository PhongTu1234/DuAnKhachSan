package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Users")
public class Users implements Serializable {
	@Id
	@Column(name = "cmt")
	@NotBlank(message = "Không được để trống")
	String cmt;

	@Column(name = "username")
	String username;

	@Column(name = "email", length = 100)
	String email;

	@Column(name = "password")
	@NotBlank(message = "Không được để trống")
	String password;

	@Column(name = "phone_number", length = 20)
	String phoneNumber;

	@Column(name = "Token")
	String token;

	@JsonIgnore
	@OneToMany(mappedBy = "Users", fetch = FetchType.EAGER)
	List<Authority> Authority;

	@JsonIgnore
	@OneToMany(mappedBy = "Users")
	List<Bookings> Bookings;

	@JsonIgnore
	@OneToMany(mappedBy = "Users")
	List<Feedback> Feedback;
}
