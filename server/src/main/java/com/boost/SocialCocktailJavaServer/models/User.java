package com.boost.SocialCocktailJavaServer.models;


import com.boost.SocialCocktailJavaServer.security.Security;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.List;

@Entity
public class User {
	@JsonView(JacksonView.freeContext.class)
	private String username;

	@JsonView(JacksonView.freeContext.class)
	private RoleType role;
	@JsonView(JacksonView.freeContext.class)
	private String email;
	@JsonView(JacksonView.freeContext.class)
	private String phoneNum;

	// This is used in the logic of the application
	// but should never be stored in the database (ensured by Transient)
	@Transient
	private String password;
	@JsonView
	private String saltedPassword;

	@JsonView(JacksonView.freeContext.class)
	private String salt;

	@JsonView(JacksonView.freeContext.class)
	private boolean isAdmin;
	
	@ManyToMany
	@JsonIgnore
	private List<Cocktail> likedCocktails;

	@OneToMany
	@JsonIgnore
	private List<Comment> userComments;

	@ManyToMany
	@JsonIgnore
	private List<User> following;
	
	@ManyToMany
	@JsonIgnore
	private List<User> followers;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(JacksonView.freeContext.class)
	private int id;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		// when password is set we automatically generate the saltedPassword field
		this.password = password;

		this.salt = Security.generateSalt();
		this.saltedPassword = Security.hash(password, this.salt);
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public List<Cocktail> getLikedCocktails() {
		return likedCocktails;
	}

	public void setLikedCocktails(List<Cocktail> likedCocktails) {
		this.likedCocktails = likedCocktails;
	}

	public List<Comment> getUserComments() {
		return userComments;
	}

	public void setUserComments(List<Comment> userComments) {
		this.userComments = userComments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<User> getFollowing() {
		return following;
	}

	public void setFollowing(List<User> following) {
		this.following = following;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public String getSaltedPassword() {
		return saltedPassword;
	}

	public String getSalt() {
		return salt;
	}
}
