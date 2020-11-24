package com.boost.SocialCocktailJavaServer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.freeContext.class)
    private int id;
    
    @JsonView(JacksonView.freeContext.class)
    private String title;

    @JsonView(JacksonView.freeContext.class)
    private String text;
    @JsonView(JacksonView.freeContext.class)
    private Date created;

    @ManyToOne
    @JsonView(JacksonView.withUserContext.class)
    private User author;

    @ManyToOne
    @JsonView(JacksonView.withCocktailContext.class)
    private Cocktail cocktail;

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCreated(Date created) {
        this.created = created;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }
}
