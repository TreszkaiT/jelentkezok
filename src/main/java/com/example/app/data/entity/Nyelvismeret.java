package com.example.app.data.entity;


import javax.persistence.Entity;

@Entity
public class Nyelvismeret extends AbstractEntity {

	private String name;

	public Nyelvismeret() {

	}

	public Nyelvismeret(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
