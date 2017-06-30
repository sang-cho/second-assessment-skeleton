package com.cooksy.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksy.twitter.dto.ProfileDto;
import com.cooksy.twitter.pojo.Profile;

@Mapper(componentModel="spring")
public interface ProfileMapper {
	Profile toProfile(ProfileDto p);
	ProfileDto toProfileDto(Profile p);
	

}
