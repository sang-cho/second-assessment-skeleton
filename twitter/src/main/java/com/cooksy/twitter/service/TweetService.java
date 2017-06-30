package com.cooksy.twitter.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cooksy.twitter.dto.TweetDto;
import com.cooksy.twitter.dto.UserDto;
import com.cooksy.twitter.mapper.TweetMapper;
import com.cooksy.twitter.pojo.Credentials;
import com.cooksy.twitter.pojo.Tweets;
import com.cooksy.twitter.pojo.User;
import com.cooksy.twitter.repo.TweetRepo;
import com.cooksy.twitter.repo.UsersRepo;

@Service
public class TweetService {
private Logger log = LoggerFactory.getLogger(getClass());

	private TweetMapper tMapper;
	private TweetRepo tRepository;
	private ValidateService validate;
	private UsersService uservice;
	private UsersRepo uRepo;
	
	public TweetService(TweetMapper tmapper, TweetRepo tRepository, ValidateService validate, UsersService uservice, UsersRepo uRepo){
		this.tMapper=tmapper;
		this.tRepository=tRepository;
		this.validate=validate;
		this.uservice=uservice;
		this.uRepo=uRepo;
	}

	public List<Tweets> getAll() {
		List<Tweets> allTweets = tRepository.findAll();
		List<Tweets> notDTweets=new ArrayList<Tweets>();
		for(int i=0; i<allTweets.size();i++){
			if(allTweets.get(i).getIsDeleted().equals(false)){
				notDTweets.add(allTweets.get(i));
			}
		}
		return notDTweets;
	}
	
	
	@Transactional
	public Tweets createTweet(TweetDto tweet){
		Tweets leTweet=new Tweets();
		if(!validate.userExists(tweet.getCredentials().getUsername())){
			return null;
		}
		leTweet.setAuthor(validate.returnAvailableUser(tweet.getCredentials().getUsername()));
		leTweet.setContent(tweet.getContent());
		return tRepository.save(leTweet);
	}

	public Tweets getTweetById(Integer id) {
		Tweets leTweet= tRepository.findOne(id);
		if(leTweet==null || leTweet.getIsDeleted().equals(true)){
			return null;
		}
		return leTweet;
		//return null;
	}

	public Tweets delete(Integer id) {
		Tweets leTweet= tRepository.findOne(id);
		if(leTweet==null){
			return null;
		}
		leTweet.setIsDeleted(true);
		return tRepository.save(leTweet);
		
	}

	public void likeTweet(Integer id, Credentials credentials) {
		Tweets tweet = tRepository.findByid(id);
		User liker = uRepo.findByCredentials_Username(credentials.getUsername());
		tweet.getUsersliked().add(liker);
		tRepository.save(tweet);
		
	}

	public List<User> getLikers(Integer id) {
		Tweets tweet=tRepository.findByid(id);
		return tweet.getUsersliked();
	}

}
	