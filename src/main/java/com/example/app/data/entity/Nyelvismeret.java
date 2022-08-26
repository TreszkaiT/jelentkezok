package com.example.app.data.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Nyelvismeret extends AbstractEntity {

	private String name;
	private String code;

	//@ManyToMany(fetch = FetchType.EAGER)
	//private Set<Person> nyelvIsmeret = new HashSet<>();

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
