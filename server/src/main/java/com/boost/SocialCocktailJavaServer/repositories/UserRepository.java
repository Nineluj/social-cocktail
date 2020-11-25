package com.boost.SocialCocktailJavaServer.repositories;

import com.boost.SocialCocktailJavaServer.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer>{
    @Query("SELECT user FROM User user WHERE user.username = :username AND user.saltedPassword = :password")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String saltedPassword);

    @Query("SELECT user.salt FROM User user WHERE user.username = :username")
    String findUserSalt(@Param("username") String username);
    
    @Query("SELECT user FROM User user WHERE user.username = :username")
    User findByUsername(@Param("username") String username);
}
