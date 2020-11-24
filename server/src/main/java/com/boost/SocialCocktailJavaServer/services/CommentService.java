package com.boost.SocialCocktailJavaServer.services;

import com.boost.SocialCocktailJavaServer.models.Cocktail;
import com.boost.SocialCocktailJavaServer.models.Comment;
import com.boost.SocialCocktailJavaServer.models.User;
import com.boost.SocialCocktailJavaServer.repositories.CocktailRepository;
import com.boost.SocialCocktailJavaServer.repositories.CommentRepository;
import com.boost.SocialCocktailJavaServer.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.servlet.http.HttpSession;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CocktailRepository cocktailRepository;
    
    public List<Comment> getRecentComments(Integer numPosts) {
    	return this.commentRepository.getRecentComments(numPosts);
    }
    
    public List<Comment> getFollowingComments(Integer numPosts, Integer userId) {
    	return this.commentRepository.getFollowingComments(numPosts, userId);
    }
    
    public List<Comment> getComments(Integer numPosts, Integer userId) {
    	List<Comment> comments = this.userRepository.findById(userId).get().getUserComments();
    	return comments.subList(0, Math.min(comments.size(), 3));
    }
    
    public Comment createComment(Integer cocktailId, Integer userId, Comment comment) {
//		comment.setAuthor(this.userRepository.findById(userId).get());
//		comment.setCocktail(this.cocktailRepository.findById(cocktailId).get()); //unsafe get call for now
//		return this.commentRepository.save(comment);
    	User user = this.userRepository.findById(userId).get();
    	Cocktail cocktail = this.cocktailRepository.findById(cocktailId).get();
    	Comment newComment = this.commentRepository.save(comment);
    	
    	List<Comment> userComments = user.getUserComments();
    	List<Comment> cocktailComments = cocktail.getComments();
    	userComments.add(newComment);
    	cocktailComments.add(newComment);
    	user.setUserComments(userComments);
    	cocktail.setComments(cocktailComments);
//		comment.setAuthor(user);
//		comment.setCocktail(cocktail); //unsafe get call for now
		this.userRepository.save(user);
		this.cocktailRepository.save(cocktail);
		comment.setAuthor(user);
		comment.setCocktail(cocktail);
		
		return this.commentRepository.save(comment);
    }
    
    public List<Comment> findCommentsByCocktailId(Integer cocktailId) {
    	return this.commentRepository.findCommentsByCocktail_Id(cocktailId);
    }

	public boolean deleteCommentById(Integer commentId) {
		if (!this.commentRepository.existsById(commentId)) {
			return false;
		}
		User user = this.commentRepository.findById(commentId).get().getAuthor();
	
		Cocktail cocktail = this.commentRepository.findById(commentId).get().getCocktail();
		
		List<Comment> userComments = user.getUserComments();
		List<Comment> cocktailComments = cocktail.getComments();
		
		userComments.removeIf(comment -> comment.getId() == commentId);
		cocktailComments.removeIf(comment -> comment.getId() == commentId);
		
		user.setUserComments(userComments);
		cocktail.setComments(cocktailComments);
		
		this.userRepository.save(user);
		this.cocktailRepository.save(cocktail);
		
		this.commentRepository.deleteById(commentId);

		return true;
	}

	public Integer findAuthorIdByCommentId(Integer commentId) {
		if (!this.commentRepository.existsById(commentId)) {
			return -1;
		}
		return this.commentRepository.findById(commentId).get().getAuthor().getId();
	}
}