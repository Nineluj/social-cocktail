package com.boost.SocialCocktailJavaServer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Glass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(JacksonView.freeContext.class)
    int id;

	@JsonView(JacksonView.freeContext.class)
    private String name;
	@JsonView(JacksonView.freeContext.class)
	@Column(length = 1024)
    private String description;
    
    @OneToMany
    @JsonIgnore
    private List<Cocktail> cocktails;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public List<Cocktail> getCocktails() {
		return cocktails;
	}
	public void setCocktails(List<Cocktail> cocktails) {
		this.cocktails = cocktails;
	}

	public Glass(String name, String description) {
		this.name = name;
		this.description = description;
		this.cocktails = new ArrayList<>();
	}

	public Glass() {}
}
