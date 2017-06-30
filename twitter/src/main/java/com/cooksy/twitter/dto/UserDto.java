package com.cooksy.twitter.dto;

import java.util.List;

import com.cooksy.twitter.pojo.Credentials;
import com.cooksy.twitter.pojo.Profile;
import com.cooksy.twitter.pojo.User;

public class UserDto {

	private ProfileDto userProfile;

	private Credentials userCreds;

	public Credentials getUserCreds() {
		return userCreds;
	}

	public void setUserCreds(Credentials userCreds) {
		this.userCreds = userCreds;
	}

	public ProfileDto getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(ProfileDto userProfile) {
		this.userProfile = userProfile;
	}
	
}
