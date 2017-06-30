package com.cooksy.twitter.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksy.twitter.dto.TweetDto;
import com.cooksy.twitter.dto.UserDto;
import com.cooksy.twitter.mapper.TweetMapper;
import com.cooksy.twitter.mapper.UserMapper;
import com.cooksy.twitter.pojo.Credentials;
import com.cooksy.twitter.pojo.Profile;
import com.cooksy.twitter.pojo.Tweets;
import com.cooksy.twitter.pojo.User;
import com.cooksy.twitter.service.UsersService;


@RestController
@RequestMapping("user")

public class UserController {
	private UsersService uService;
	private UserMapper umapper;
	private TweetMapper tMapper;
	
	public UserController(UsersService userService, UserMapper usermapper, TweetMapper tMapper) {
		this.uService = userService;
		this.umapper = usermapper;
		this.tMapper = tMapper;
	}
	
	//no optional arguments
	@PostMapping("user")
	public User post(@RequestBody UserDto dto, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return uService.createUser(umapper.toUser(dto));
	}
	
	@GetMapping
	public List<User> getAll() {
		return uService.getAll();
	}
	
	@GetMapping("users/@{username}")
	public User getUser(@PathVariable String username, HttpServletResponse response){
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return uService.getByUserName(username);	
	}
	
	//need to change so deleted users dont show
	//no credential check
	@DeleteMapping("users/@{username}")
	public User deleteUser(@PathVariable String username, HttpServletResponse response){
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return uService.delete(username);
	}
	
	//kinda works, changes id for some odd reason....
	//no credential check
	@PatchMapping("users/@{username}")
	public User patchUser(@PathVariable String username, @RequestBody UserDto profile, HttpServletResponse response){
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return uService.patch(username, profile);
	}
	
	//kind of works, used @jsonignore to get rid of cyclical reference, should change.
	//no credential check
	@PostMapping("/@{username}/follow")
	public void  postFollower(@PathVariable String username, @RequestBody Credentials userCredentials){
		 uService.follow(username, userCredentials);
	}
	
	//no credential check
	@DeleteMapping("/@{username}/follow")
	public void  deleteFollower(@PathVariable String username, @RequestBody Credentials userCredentials){
		 uService.unfollow(username, userCredentials);
	}
	
	//change @jsonignore
	@GetMapping("@{username}/followers") 
	public List<User> getUserFollowers(@PathVariable String username) {
		uService.credCheck(username, uService.getByUserName(username).getUserCreds());
		return uService.getFollowers(username);
	}
	
	//change @jsonignore
	@GetMapping("@{username}/following") 
	public List<User> getUserFollowing(@PathVariable String username ) {
		return uService.getFollowing(username);
	}
	
	//"someo
	@GetMapping({"@{username}/tweets"})
		public List<TweetDto> getUserTweets(@PathVariable String username){
			return uService.getTweets(username).stream()
					.map(tMapper::toTweetDto)
					.collect(Collectors.toList());
		}
	}
	

