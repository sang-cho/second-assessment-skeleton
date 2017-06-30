package com.cooksy.twitter.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksy.twitter.dto.TweetDto;
import com.cooksy.twitter.dto.UserDto;
import com.cooksy.twitter.mapper.TweetMapper;
import com.cooksy.twitter.mapper.UserMapper;
import com.cooksy.twitter.pojo.Credentials;
import com.cooksy.twitter.pojo.Tweets;
import com.cooksy.twitter.pojo.User;
import com.cooksy.twitter.service.TweetService;


@RestController
@RequestMapping("tweets")
public class TweetController {
	private TweetService tService;
	private TweetMapper tmapper;
	private UserMapper umapper;
	
	public TweetController(TweetService tweetservice,TweetMapper tweetmapper, UserMapper umapper){
		this.tService= tweetservice;
		this.tmapper= tweetmapper;
		this.umapper=umapper;
	}
	
	
	@PostMapping("tweets")
	public TweetDto post(@RequestBody TweetDto dto, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return tmapper.toTweetDto(tService.createTweet(dto));
	}
	
	@GetMapping
	public List<TweetDto> getAll() {
		return tService.getAll().stream()
				.map(tmapper::toTweetDto)
				.collect(Collectors.toList());
	}
	
	
	@GetMapping("tweets/@{id}")
	public TweetDto getTweetsId(@PathVariable Integer id) {
		return tmapper.toTweetDto(tService.getTweetById(id));
	}
	
	@DeleteMapping("tweets/@{id}")
	
	public Tweets deleteTweetId(@PathVariable Integer id){
			return tService.delete(id);
	}

	@PostMapping("tweets/@{id}/like")
	public void like(@PathVariable Integer id, @RequestBody Credentials credentials){
		tService.likeTweet(id, credentials);
	}
	
	@GetMapping("tweets/@{id}/likes")
	public List<UserDto> likers(@PathVariable Integer id){
		
		return tService.getLikers(id).stream()
				.map(umapper::toUserDto)
				.collect(Collectors.toList());
	}
	
	
	
}
