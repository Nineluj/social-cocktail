package com.boost.SocialCocktailJavaServer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.boost.SocialCocktailJavaServer.models.Tip;

public interface TipRepository extends CrudRepository<Tip, Integer> {
	
}
