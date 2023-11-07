package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Services")
public class Services implements Serializable {

    @Id
    @Column(name = "service_id")
    Integer id;

    @Column(name = "service_name")
    String name;
    
    @JsonIgnore
	@OneToMany(mappedBy = "Services")
	List<Service_Rooms> Service_Rooms;
}