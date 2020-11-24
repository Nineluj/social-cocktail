package com.boost.SocialCocktailJavaServer.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Tip {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(JacksonView.freeContext.class)
	private int id;
	
	@JsonView(JacksonView.freeContext.class)
	private String text;
	
	@ManyToOne
	@JsonView(JacksonView.withUserContext.class)
	private Bartender bartender;
	
	@ManyToOne
	@JsonIgnore
	private Cocktail cocktail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Bartender getBartender() {
		return bartender;
	}

	public void setBartender(Bartender bartender) {
		this.bartender = bartender;
	}

	public Cocktail getCocktail() {
		return cocktail;
	}

	public void setCocktail(Cocktail cocktail) {
		this.cocktail = cocktail;
	}
}
