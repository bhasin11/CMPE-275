package io.javabrains.topic;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // to tell JPA it is an entity class
public class Topic {
	
	@Id // primary key
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String name;
	private String description;
	public Topic(){
		
	}
	public Topic(String id, String name, String description){
		this.id=id;
		this.name=name;
		this.description=description;
	}
	
}
