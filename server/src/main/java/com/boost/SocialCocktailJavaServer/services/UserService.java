package com.boost.SocialCocktailJavaServer.services;

import com.boost.SocialCocktailJavaServer.models.Bartender;
import com.boost.SocialCocktailJavaServer.models.Cocktail;
import com.boost.SocialCocktailJavaServer.models.User;
import com.boost.SocialCocktailJavaServer.repositories.BartenderRepository;
import com.boost.SocialCocktailJavaServer.repositories.CocktailRepository;
import com.boost.SocialCocktailJavaServer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CocktailRepository cocktailRepository;

	@Autowired
	private BartenderRepository bartenderRepository;

	// Authenticate that a correct username password pair was entered.
	public User authenticateUser(User user) {
		return this.userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}

	// Register a user.
	public User registerUser(User user) {
		if (this.userRepository.findByUsername(user.getUsername()) == null) {
			user.setAdmin(false);
			return this.userRepository.save(user);
		}
		return null;
	}

	// Register a Bartender (created as unverified)
	public Bartender registerBartender(Bartender bartender) {
		if (this.bartenderRepository.findByUsername(bartender.getUsername()) == null) {
			bartender.setVerified(false);
			return this.bartenderRepository.save(bartender);
		}
		return null;
	}

	public void verifyBartender(int uid) {
		this.bartenderRepository.findById(uid).ifPresent(bartender -> {
		    bartender.setVerified(true);
		    this.bartenderRepository.save(bartender);
        });
	}

	public List<Bartender> findUnverifiedBartenders() {
		return this.bartenderRepository.findUnverifiedBartenders();
	}

	public User findUserById(Integer id) {
		if (this.userRepository.findById(id).isPresent()) {
			return this.userRepository.findById(id).get();
		}
		return null;
	}

	public User updateUser(User user) {
		User toUpdate = this.userRepository.findByUsername(user.getUsername());
		toUpdate.setEmail(user.getEmail());
		toUpdate.setPhoneNum(user.getPhoneNum());
		this.userRepository.save(toUpdate);

		return toUpdate;
	}

	public void addLikedCocktail(Integer cocktailId, Integer userId) {
		User user = this.userRepository.findById(userId).get();
		Cocktail cocktail = this.cocktailRepository.findById(cocktailId).get();

		List<Cocktail> likedCocktails = user.getLikedCocktails();
		likedCocktails.add(cocktail);
		user.setLikedCocktails(likedCocktails);
		this.userRepository.save(user);
	}

	public List<User> getFollowers(Integer userId) {
		return this.userRepository.findById(userId).get().getFollowers();
	}

	public List<User> getFollowing(Integer userId) {
		return this.userRepository.findById(userId).get().getFollowing();
	}

	public User addFollowing(Integer userFollowingId, Integer userId) {
		User followingUser;

		if (!this.userRepository.existsById(userFollowingId)) {
			return null;
		}
		followingUser = this.userRepository.findById(userFollowingId).get();
		User curUser = this.userRepository.findById(userId).get();

		if (curUser.getFollowing().contains(followingUser)) {
			return followingUser;
		}


		// Add the new person to the currently logged in User's following list.
		List<User> following = curUser.getFollowing();
		following.add(followingUser);
		curUser.setFollowing(following);

		// Add the currently logged in person to the new person's followers list.
		List<User> followers = followingUser.getFollowers();
		followers.add(curUser);
		followingUser.setFollowers(followers);

		this.userRepository.save(curUser);
		this.userRepository.save(followingUser);

		return followingUser;
	}

	public List<Cocktail> getLikedCocktails(Integer userId, Integer numLikes) {
		List<Cocktail> cocktails = this.userRepository.findById(userId).get().getLikedCocktails();
		System.out.println(cocktails.size());
		return cocktails.subList(0, Math.min(cocktails.size(), numLikes));
	}
}
