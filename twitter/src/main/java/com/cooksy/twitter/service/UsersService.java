package com.cooksy.twitter.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cooksy.twitter.dto.UserDto;
import com.cooksy.twitter.mapper.ProfileMapper;
import com.cooksy.twitter.mapper.UserMapper;
import com.cooksy.twitter.pojo.Credentials;
import com.cooksy.twitter.pojo.Profile;
import com.cooksy.twitter.pojo.Tweets;
import com.cooksy.twitter.pojo.User;
import com.cooksy.twitter.repo.TweetRepo;
import com.cooksy.twitter.repo.UsersRepo;

@Service
public class UsersService {
	private Logger log = LoggerFactory.getLogger(getClass());
	private ValidateService validate;

	private UsersRepo uRepository;
	private UserMapper uMapper;
	private ProfileMapper pMapper;
	private TweetRepo tRepository;
	private TweetService tService;
	public UsersService(UsersRepo uRepository, ValidateService validate, UserMapper Mapper, ProfileMapper pMapper, TweetRepo tRepository) {
		this.uRepository = uRepository;
		this.validate = validate;
		this.uMapper=Mapper;
		this.pMapper = pMapper;
		this.tRepository=tRepository;
	}

	public List<User> getAll() {
		List<User> allUsers= uRepository.findAll();
		List<User> notDUsers = new ArrayList<User>();
		for(int i=0;i<allUsers.size();i++){
			if(allUsers.get(i).getIsDeleted().equals(false)){
				notDUsers.add(allUsers.get(i));
			}
		}
		return notDUsers;
	}

	public User createUser(User user) {
		//checks if user is deleted, reactivates if it is.
		User check=getByUserName(user.getUserCreds().getUsername());
		if(check != null && check.getIsDeleted().equals(true)){
			 check.setIsDeleted(false);
			 return uRepository.save(check);
		}
			
		//see if username is available
		else if (validate.userAvailable(user.getUserCreds().getUsername())) {
			return uRepository.save(user);
		}
		return null;
	}
	
	//method to get by user by username.
	public User getByUserName(String username) {
		return uRepository.findByCredentials_Username(username);
	}

	//"deletes" user by setting delete flag to true.
	public User delete(String username) {
		User theuser = getByUserName(username);
		theuser.setIsDeleted(true);
		uRepository.save(theuser);
		return theuser;
	}
	
	
	public User patch(String username, UserDto profile) {
		User theuser = getByUserName(username);
		if (theuser != null || validate.userAvailable(username)) {
			//my attempt of fixing my stupid mistake of not validating from the start...doesn't work. lord help me.
			//if(credCheck(profile.getUserCreds().getUsername() , profile.getUserCreds()) == true){
			theuser.setUserProfile(pMapper.toProfile(profile.getUserProfile()));
			uRepository.save(theuser);
			return theuser;
			}
		//}
		return null;

	}

	public void follow(String username, Credentials userCredentials) {
		User userFollowed= getByUserName(userCredentials.getUsername());
		User followingUser = getByUserName(username);

		if (followingUser != null && validate.userExists(username)) {
			followingUser.getFollowers().add(userFollowed);
			userFollowed.getFollowing().add(followingUser);
		}

		uRepository.saveAndFlush(followingUser);
		uRepository.saveAndFlush(userFollowed);

	}
	public void unfollow(String username, Credentials userCredentials) {
		User userFollowed= getByUserName(userCredentials.getUsername());
		User followingUser = getByUserName(username);

		if (followingUser != null && validate.userExists(username)) {
			followingUser.getFollowers().remove(userFollowed);
			userFollowed.getFollowing().remove(followingUser);
		}

		uRepository.saveAndFlush(followingUser);
		uRepository.saveAndFlush(userFollowed);

		
	}

	//checks if credentials match the user. Didn't use. RIP.
	public boolean credCheck(String username, Credentials credentials) { 
		if(getByUserName(username).getUserCreds().equals(credentials)){
			return true; 
			}
		return false; 
		}
	 

	public List<User> getFollowers(String username) {
		User userList = getByUserName(username);
		List<User> list = userList.getFollowers();
		List<User> followerList = new ArrayList<>();
		for (User user : list) {
			System.out.println(user);
			followerList.add(user);	
		}
		return followerList;
	}

	public List<User> getFollowing(String username) {
		User userList = getByUserName(username);
		List<User> list = userList.getFollowing();
		List<User> followingList = new ArrayList<>();
		
		for (User user : list) {
			followingList.add(user);
			System.out.println(user);

		}
		return followingList;
	}

	public List<Tweets> getTweets(String username) {
		User user = getByUserName(username);
		return user.getUserTweets();
	}
}
