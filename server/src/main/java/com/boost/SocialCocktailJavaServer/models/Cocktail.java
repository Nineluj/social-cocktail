package com.boost.SocialCocktailJavaServer.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cocktail {
	@Id
	@JsonView(JacksonView.freeContext.class)
	private Integer id;

	@JsonView(JacksonView.freeContext.class)
	private String name;

	@OneToMany
	@JsonView(JacksonView.withCommentContext.class)
	private List<Comment> comments = new ArrayList<>();
	
	@ManyToMany
	@JsonView(JacksonView.withUserContext.class)
	private List<User> usersLikedBy = new ArrayList<>();
	
	@ManyToOne
	@JsonView(JacksonView.freeContext.class)
	private Glass glassType;
	
	@OneToMany
	@JsonView(JacksonView.withTipContext.class)
	private List<Tip> tips;

	public Cocktail() { }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<User> getUsersLikedBy() {
		return usersLikedBy;
	}

	public void setUsersLikedBy(List<User> usersLikedBy) {
		this.usersLikedBy = usersLikedBy;
	}

	public Glass getGlassType() {
		return glassType;
	}

	public void setGlassType(Glass glassType) {
		this.glassType = glassType;
	}

	public List<Tip> getTips() {
		return tips;
	}

	public void setTips(List<Tip> tips) {
		this.tips = tips;
	}
}
