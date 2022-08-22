package com.example.app.data.entity;


import javax.persistence.Entity;

@Entity
public class Nyelvismeret extends AbstractEntity {

	private String name;
	private String code;

	public Nyelvismeret() {

	}

	public Nyelvismeret(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
