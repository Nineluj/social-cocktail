package com.boost.SocialCocktailJavaServer.controllers;

import com.boost.SocialCocktailJavaServer.models.Cocktail;
import com.boost.SocialCocktailJavaServer.models.JacksonView;
import com.boost.SocialCocktailJavaServer.models.Tip;
import com.boost.SocialCocktailJavaServer.models.User;
import com.boost.SocialCocktailJavaServer.services.BartenderService;
import com.boost.SocialCocktailJavaServer.services.CocktailService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
public class CocktailController {
	@Autowired
	private CocktailService cocktailService;
	
	@Autowired
	private BartenderService bartenderService;

	@PostMapping("/api/cocktails")
	@JsonView(JacksonView.forCocktailRequest.class)
	public ResponseEntity<Cocktail> createCocktail(@RequestParam(value="glassType") String glassType, @RequestBody Cocktail cocktail) {
		Cocktail newCocktail = this.cocktailService.createCocktail(cocktail, glassType);
		if (newCocktail != null) {
			return new ResponseEntity<>(newCocktail, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@GetMapping("/api/cocktails/{cocktailId}")
	@JsonView(JacksonView.forCocktailRequest.class)
	public ResponseEntity<Cocktail> findCocktailById(@PathVariable("cocktailId") int cocktailId) {
		Cocktail response = this.cocktailService.findCocktailById(cocktailId);

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PostMapping("/api/cocktails/{cocktailId}/likes")
	public ResponseEntity userLikeCocktail(@PathVariable("cocktailId") int cocktailId, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			if (this.cocktailService.likeCocktail(cocktailId, (Integer) session.getAttribute("userId"))) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	}

	@GetMapping("/api/cocktails/{cocktailId}/likes")
	@JsonView(JacksonView.forUserRequest.class)
	public List<User> findCocktailUsersLikes(@PathVariable("cocktailId") int cocktailId) {
		return this.cocktailService.findCocktailUsersLikes(cocktailId);
	}
	
	@PostMapping("/api/cocktails/{cocktailId}/addTip")
	@JsonView(JacksonView.forCocktailRequest.class)
	public ResponseEntity<Tip> createTip(@PathVariable("cocktailId") Integer cocktailId, @RequestBody Tip tip, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		else if (!this.bartenderService.isVerifiedBartender((Integer)session.getAttribute("userId"))) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(this.cocktailService.createTip(cocktailId, tip, (Integer) session.getAttribute("userId")), HttpStatus.OK);
	}
}
