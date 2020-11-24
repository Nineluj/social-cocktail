package com.boost.SocialCocktailJavaServer.services;

import com.boost.SocialCocktailJavaServer.models.Bartender;
import com.boost.SocialCocktailJavaServer.models.Cocktail;
import com.boost.SocialCocktailJavaServer.models.Glass;
import com.boost.SocialCocktailJavaServer.models.Tip;
import com.boost.SocialCocktailJavaServer.models.User;
import com.boost.SocialCocktailJavaServer.repositories.BartenderRepository;
import com.boost.SocialCocktailJavaServer.repositories.CocktailRepository;
import com.boost.SocialCocktailJavaServer.repositories.CommentRepository;
import com.boost.SocialCocktailJavaServer.repositories.GlassRepository;
import com.boost.SocialCocktailJavaServer.repositories.TipRepository;
import com.boost.SocialCocktailJavaServer.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CocktailService {
	@Autowired
	private CocktailRepository cocktailRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private GlassRepository glassRepository;
	
	@Autowired
	private BartenderRepository bartenderRepository;

	@Autowired
	private TipRepository tipRepository;
	
	public Cocktail createCocktail(Cocktail cocktail, String glassType) {
		if (this.cocktailRepository.existsById(cocktail.getId())) {
			return this.cocktailRepository.findById(cocktail.getId()).get();
		}
		if (this.glassRepository.findByName(glassType).isPresent()) {
			this.cocktailRepository.save(cocktail);
			Glass glass = this.glassRepository.findByName(glassType).get();
			List<Cocktail> cocktails = glass.getCocktails();
			cocktails.add(cocktail);
			glass.setCocktails(cocktails);
			cocktail.setGlassType(glass);
			this.glassRepository.save(glass);
			this.cocktailRepository.save(cocktail);
			return cocktail;
			
		}
		this.cocktailRepository.save(cocktail);
		return cocktail;
	}

	public Cocktail findCocktailById(int cocktailId) {
		return this.cocktailRepository.findById(cocktailId).orElse(null);
	}

	public boolean likeCocktail(int cocktailId, int userId) {
		return this.userRepository.findById(userId)
				.map(user -> this.cocktailRepository.findById(cocktailId)
						.map(cocktail -> {
							List<User> currentLikes = cocktail.getUsersLikedBy();
							List<Cocktail> currentLikesUser = user.getLikedCocktails();

							if (!currentLikes.contains(user)) {
								currentLikes.add(user);
								cocktail.setUsersLikedBy(currentLikes);
								this.cocktailRepository.save(cocktail);
								
								currentLikesUser.add(cocktail);
								user.setLikedCocktails(currentLikesUser);
								this.userRepository.save(user);
							}

							return true;
						}).orElse(false))
				.orElse(false);
	}

	public List<User> findCocktailUsersLikes(int cocktailId) {
		return this.cocktailRepository.findById(cocktailId)
				.map(Cocktail::getUsersLikedBy).orElse(Collections.emptyList());
	}
	
	public Tip createTip(Integer cocktailId, Tip tip, Integer userId) {
		Optional<Cocktail> optCocktail = this.cocktailRepository.findById(cocktailId);
		Optional<Bartender> optBartender = this.bartenderRepository.findById(userId);
		
		if (optCocktail.isPresent() && optBartender.isPresent()) {
			Cocktail cocktail = optCocktail.get();
			Bartender bartender = optBartender.get();
			
			tip.setCocktail(cocktail);
			tip.setBartender(bartender);
			
			this.tipRepository.save(tip);
			
			List<Tip> cocktailTips = cocktail.getTips();
			List<Tip> bartenderTips = bartender.getTips();
			
			cocktailTips.add(tip);
			bartenderTips.add(tip);
			
			cocktail.setTips(cocktailTips);
			bartender.setTips(bartenderTips);
			
			this.cocktailRepository.save(cocktail);
			this.bartenderRepository.save(bartender);
			
//			tip.setCocktail(cocktail);
//			tip.setBartender(bartender);
			
			return this.tipRepository.save(tip);
		}
		return null;
	}
}
