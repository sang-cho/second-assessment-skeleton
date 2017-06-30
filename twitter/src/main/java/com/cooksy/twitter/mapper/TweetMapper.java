package com.cooksy.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksy.twitter.dto.TweetDto;
import com.cooksy.twitter.pojo.Tweets;

@Mapper(componentModel = "spring")
public interface TweetMapper {
	Tweets toTweet(TweetDto t);
	TweetDto toTweetDto(Tweets t);
	

}
