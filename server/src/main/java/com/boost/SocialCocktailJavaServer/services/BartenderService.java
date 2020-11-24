package com.boost.SocialCocktailJavaServer.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boost.SocialCocktailJavaServer.models.Bartender;
import com.boost.SocialCocktailJavaServer.repositories.BartenderRepository;


@Service
public class BartenderService {
	@Autowired
	private BartenderRepository bartenderRepository;
	
	public boolean isVerifiedBartender(Integer bartenderId) {
		Optional<Bartender> optBartender = this.bartenderRepository.findById(bartenderId);
		
		return optBartender.isPresent() && optBartender.get().isVerified();
		
	}
}
