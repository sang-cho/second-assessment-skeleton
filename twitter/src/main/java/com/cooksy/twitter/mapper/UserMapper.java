package com.cooksy.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksy.twitter.dto.UserDto;
import com.cooksy.twitter.pojo.User;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface UserMapper {
	
	User toUser(UserDto u);
	
	UserDto toUserDto(User u);

}
