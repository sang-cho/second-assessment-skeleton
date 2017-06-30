package com.cooksy.twitter.service;

import org.springframework.stereotype.Service;

import com.cooksy.twitter.pojo.User;
import com.cooksy.twitter.repo.UsersRepo;
	
@Service
public class ValidateService {
	private UsersRepo uRepository;
	
	public ValidateService(UsersRepo uRep){
		this.uRepository=uRep;
	}
	
	public boolean userExists(String username) {
		User user = uRepository.findByCredentials_Username(username);
		//System.out.println(username);
		if(user == null || user.getIsDeleted()){
			return false;
		}
		else 
			return true;
	}
	public boolean userAvailable(String username) {
		User user = uRepository.findByCredentials_Username(username);
		if(user == null)
			return true;
		else 
			return false;
	}
	public User returnAvailableUser(String username){
		if(userExists(username)){
			User user = uRepository.findByCredentials_Username(username);
			return user;
		}
		return null;
	}

}
