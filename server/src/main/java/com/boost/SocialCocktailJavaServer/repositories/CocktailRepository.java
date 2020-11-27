package com.boost.SocialCocktailJavaServer.repositories;

import com.boost.SocialCocktailJavaServer.models.Cocktail;
import org.springframework.data.repository.CrudRepository;

public interface CocktailRepository extends CrudRepository<Cocktail, Integer> {}
