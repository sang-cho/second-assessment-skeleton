package com.cooksy.twitter.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksy.twitter.pojo.Tweets;

public interface TweetRepo extends JpaRepository<Tweets, Integer> {
	 Tweets findByid(Integer id);
	 
}
