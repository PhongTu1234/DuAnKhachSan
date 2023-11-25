package com.poly.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Authorities")
public class Authority implements Serializable {

	@Id
	@Column(name = "Id")
	Integer id;

	@ManyToOne
	@JoinColumn(name = "cmt")
	Users Users;

	@ManyToOne
	@JoinColumn(name = "role_id")
	Role Role;
}