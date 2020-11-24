package com.boost.SocialCocktailJavaServer.controllers;


import java.util.List;

import javax.servlet.http.HttpSession;

import com.boost.SocialCocktailJavaServer.models.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.boost.SocialCocktailJavaServer.models.Comment;
import com.boost.SocialCocktailJavaServer.services.CommentService;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;

	@GetMapping("/api/comments/recent/{numPosts}")
	@JsonView(JacksonView.forCommentRequest.class)
	public List<Comment> getRecentComments(@PathVariable("numPosts") Integer numPosts) {
		return this.commentService.getRecentComments(numPosts);
	}
	
	@GetMapping("/api/comments/following/{numPosts}")
	@JsonView(JacksonView.forCommentRequest.class)
	public List<Comment> getFollowingComments(@PathVariable("numPosts") Integer numPosts, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return null;
		}
		return this.commentService.getFollowingComments(numPosts, (Integer)session.getAttribute("userId"));
	}
	
	@GetMapping("/api/comments/{numPosts}")
	@JsonView(JacksonView.forCommentRequest.class)
	public List<Comment> getComments(@PathVariable("numPosts") Integer numPosts, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return null;
		}
		return this.commentService.getComments(numPosts, (Integer)session.getAttribute("userId"));
	}
	
	@GetMapping("/api/user/{userId}/comments/{numPosts}")
	@JsonView(JacksonView.forCommentRequest.class)
	public List<Comment> getCommentsByUserId(@PathVariable("userId") Integer userId, @PathVariable("numPosts") Integer numPosts) {
		return this.commentService.getComments(numPosts, userId);
	}
	
	@PostMapping("/api/cocktail/{cocktailId}/comments")
	@JsonView(JacksonView.forCommentRequest.class)
	public ResponseEntity<Comment> createComment(@PathVariable("cocktailId") Integer cocktailId, @RequestBody Comment comment, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<>(this.commentService.createComment(cocktailId, (Integer) session.getAttribute("userId"), comment), HttpStatus.OK);
	}

	@GetMapping("/api/cocktail/{cocktailId}/comments")
	@JsonView(JacksonView.forCommentRequest.class)
	public List<Comment> findCommentsByCocktailId(@PathVariable("cocktailId") Integer cocktailId) {
		return this.commentService.findCommentsByCocktailId(cocktailId);
	}
	
	@DeleteMapping("/api/comments/{commentId}")
	public ResponseEntity<Void> deleteCommentById(@PathVariable("commentId") Integer commentId, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (this.commentService.findAuthorIdByCommentId(commentId) != 
			(Integer)session.getAttribute("userId")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (this.commentService.deleteCommentById(commentId)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
