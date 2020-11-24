package com.boost.SocialCocktailJavaServer.repositories;

import com.boost.SocialCocktailJavaServer.models.Comment;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
	
    @Query(value = "SELECT * FROM comment as comment ORDER BY comment.created DESC LIMIT :num_posts", nativeQuery = true)
	public List<Comment> getRecentComments(@Param("num_posts") Integer numPosts);
    
    @Query(value = "SELECT * FROM comment as comment JOIN user_following ON comment.author_id=user_following.following_id WHERE user_following.user_id=:user_id ORDER BY comment.created DESC LIMIT :num_posts", nativeQuery = true)
    public List<Comment> getFollowingComments(@Param("num_posts") Integer numPosts, @Param("user_id") Integer userId);
    
    public List<Comment> findCommentsByCocktail_Id(@Param("cocktail_id") Integer cocktailId);
//    @Query(value= "SELECT * FROM Comment as comment JOIN WHERE comment.author_id =:author_id ORDER BY comment.created")
//    public List<Comment> getFollowingRecentComments(@Param("num_posts") Integer numPosts);
}
