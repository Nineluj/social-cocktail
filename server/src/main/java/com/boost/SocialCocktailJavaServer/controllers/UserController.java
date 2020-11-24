package com.boost.SocialCocktailJavaServer.controllers;

import com.boost.SocialCocktailJavaServer.models.Bartender;
import com.boost.SocialCocktailJavaServer.models.Cocktail;
import com.boost.SocialCocktailJavaServer.models.JacksonView;
import com.boost.SocialCocktailJavaServer.models.User;
import com.boost.SocialCocktailJavaServer.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@JsonView(JacksonView.forUserRequest.class)
	@PostMapping("/api/users/login")
	public ResponseEntity authenticateUser(@RequestBody User user, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			return new ResponseEntity(HttpStatus.OK);
		}
		User retrievedUser = this.userService.authenticateUser(user);
		if (retrievedUser != null) {
			session.setAttribute("userId", retrievedUser.getId());
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	@JsonView(JacksonView.forUserRequest.class)
	@PostMapping("/api/users/register")
	public ResponseEntity registerUser(@RequestBody User user, HttpSession session) {
		User retrievedUser = this.userService.registerUser(user);
		if (retrievedUser != null) {
			session.setAttribute("userId", retrievedUser.getId());
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	@JsonView(JacksonView.forUserRequest.class)
	@PostMapping("/api/users/bartender")
	public ResponseEntity registerBartender(@RequestBody Bartender bartender, HttpSession session) {
		Bartender retrievedBartender = this.userService.registerBartender(bartender);
		if (retrievedBartender != null) {
			session.setAttribute("userId", retrievedBartender.getId());
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	@JsonView(JacksonView.forUserRequest.class)
	@GetMapping("/api/users/bartenders")
	public List<Bartender> findUnverifiedBartenders() {
		return this.userService.findUnverifiedBartenders();
	}

	@PostMapping("/api/users/bartenders/{uid}")
	public ResponseEntity verifyBartender(@PathVariable("uid") int uid, HttpSession session) {
		User requestingUser = this.userService.findUserById((Integer)session.getAttribute("userId"));
		if (requestingUser.isAdmin()) {
			this.userService.verifyBartender(uid);
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
	}

	// Get the currently logged in User.
	@JsonView(JacksonView.forUserRequest.class)
	@GetMapping("/api/user")
	public ResponseEntity<User> getLoggedInUser(HttpSession session, HttpServletResponse response) {
		if (session.getAttribute("userId") != null) {
			return new ResponseEntity<>(this.userService.findUserById((Integer)session.getAttribute("userId")), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Logout the currently logged in User, invalidating the HttpSession.
	@JsonView(JacksonView.forUserRequest.class)
	@GetMapping("/api/user/logout")
	public ResponseEntity logoutUser(HttpSession session) {
		if (session.getAttribute("userId") != null) {
			session.invalidate();
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/users/{id}")
	@JsonView(JacksonView.forUserRequest.class)
	public ResponseEntity<User> findUserById(@PathVariable Integer id) {
		User response = this.userService.findUserById(id);

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	// Update the currently logged-in User's information
	@JsonView(JacksonView.forUserRequest.class)
	@PutMapping("/api/user")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		User response = this.userService.updateUser(user);

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	// Add the cocktail with the given id to the logged-in User's
	// liked cocktails.
	@JsonView(JacksonView.forUserRequest.class)
	@PostMapping("/api/user/likes/cocktail/{id}")
	public void addLikedCocktail(@PathVariable("id") Integer id, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			this.userService.addLikedCocktail(id, (Integer)session.getAttribute("userId"));
		}
	}
	
	@GetMapping("/api/user/followers")
	@JsonView(JacksonView.forUserRequest.class)
	public List<User> getFollowers(HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return null;
		}
		return this.userService.getFollowers((Integer)session.getAttribute("userId"));
	}
	
	@GetMapping("/api/user/followers/{userId}")
	@JsonView(JacksonView.forUserRequest.class)
	public List<User> getFollowers(@PathVariable("userId") Integer userId) {
		return this.userService.getFollowers(userId);
	}
	
	@GetMapping("/api/user/following")
	@JsonView(JacksonView.forUserRequest.class)
	public List<User> getFollowing(HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return null;
		}
		return this.userService.getFollowing((Integer)session.getAttribute("userId"));
	}
	
	@GetMapping("/api/user/following/{userId}")
	@JsonView(JacksonView.forUserRequest.class)
	public List<User> getFollowing(@PathVariable("userId") Integer userId) {
		return this.userService.getFollowing(userId);
	}
	
	@PostMapping("/api/user/following/{userFollowingId}")
	@JsonView(JacksonView.forUserRequest.class)
	public User addFollowing(@PathVariable("userFollowingId") Integer userFollowingId, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return null;
		}
		return this.userService.addFollowing(userFollowingId, (Integer)session.getAttribute("userId"));
	}
	
	@GetMapping("/api/users/{userId}/likes/{numLikes}")
	@JsonView(JacksonView.forCocktailRequest.class)
	public List<Cocktail> getLikedCocktails(@PathVariable("userId") Integer userId, @PathVariable("numLikes") Integer numLikes) {
		return this.userService.getLikedCocktails(userId, numLikes);
	}
	
	
}
