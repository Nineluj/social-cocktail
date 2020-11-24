package com.boost.SocialCocktailJavaServer.repositories;

import com.boost.SocialCocktailJavaServer.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer>{
    @Query("SELECT user FROM User user WHERE user.username = :username AND user.password = :password") 
	public User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    
    @Query("SELECT user FROM User user WHERE user.username = :username")
    public User findByUsername(@Param("username") String username);
}
