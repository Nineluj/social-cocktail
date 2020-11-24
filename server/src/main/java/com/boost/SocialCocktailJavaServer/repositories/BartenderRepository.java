package com.boost.SocialCocktailJavaServer.repositories;

import com.boost.SocialCocktailJavaServer.models.Bartender;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BartenderRepository extends CrudRepository<Bartender, Integer> {
    @Query("SELECT bartender FROM Bartender bartender WHERE bartender.username = :username")
    Bartender findByUsername(@Param("username") String username);

    @Query("SELECT bartender FROM Bartender bartender WHERE bartender.verified = false")
    List<Bartender> findUnverifiedBartenders();
}
