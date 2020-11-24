package com.boost.SocialCocktailJavaServer.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.boost.SocialCocktailJavaServer.models.Glass;

public interface GlassRepository extends CrudRepository<Glass, Integer> {
	public Optional<Glass> findByName(@Param("name") String name);

}
