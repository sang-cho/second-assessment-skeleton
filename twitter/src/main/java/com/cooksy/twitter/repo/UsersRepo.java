package com.cooksy.twitter.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksy.twitter.pojo.User;

public interface UsersRepo extends JpaRepository<User,Long> {

	User findByCredentials_Username(String username);
	//User findByCredentials_UsernameAndCredentials_Password(String username, String password);
	
}
