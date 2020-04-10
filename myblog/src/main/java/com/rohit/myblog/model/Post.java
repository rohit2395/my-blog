package com.rohit.myblog.model;

import java.time.Instant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.rohit.myblog.common.BlogConstants;

@Entity
@Table(name = BlogConstants.POST_TABLE,schema=BlogConstants.SCHEMA)
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column
	private String title;
	
	@Lob
	@NotEmpty
	@Column
	private String content;
	
	@Column
	private Instant createdOn;
	
	@Column
	private Instant updatedOn;
	
	@NotBlank
	@Column
	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}

	public Instant getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Instant updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}
