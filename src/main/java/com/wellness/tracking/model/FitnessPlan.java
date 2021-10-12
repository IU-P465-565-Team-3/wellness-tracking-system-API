package com.wellness.tracking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "fitness_plan_")
@Data
@Accessors(chain = true)
public class FitnessPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "type_id")
	private long typeId;

	@Column(name = "name")
	private String name;

	@Column(name = "publisher_id")
	private long publisherId;

	@Column(name = "description")
	private String description;

	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "image_annotation")
	private String imageAnnotation;

	public long getId(){
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getTypeId(){
		return typeId;
	}

	public void setTypeId(long typeId){
		this.typeId = typeId;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public long getPublisherId(){
		return publisherId;
	}

	public void setPublisherId(long publisherId){
		this.publisherId = publisherId;
	}

	public String getDescription(){
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageAnnotation(){
		return imageAnnotation;
	}

	public void setImageAnnotation(String imageAnnotation){
		this.imageAnnotation = imageAnnotation;
	}
}

