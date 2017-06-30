package com.cooksy.twitter.pojo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//magic annotation that saves me time
	@Version
	@Column(nullable=false, updatable=false)
	private Timestamp time;

	
	@OneToOne(cascade = CascadeType.ALL)
	private Profile userProfile;
	
	@OneToMany(mappedBy="author") 
	private List<Tweets> userTweets;
	


	@Embedded
	private Credentials credentials;
		
	private Boolean isDeleted=false;

	@ManyToMany
	@JsonIgnore
	private List<User> following;
	
	@ManyToMany
	@JsonIgnore
	private List<User> followers;
	
	@ManyToMany
	private List<Tweets> likedTweets;
	
	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public List<Tweets> getLikedTweets() {
		return likedTweets;
	}

	public void setLikedTweets(List<Tweets> likedTweets) {
		this.likedTweets = likedTweets;
	}

	public List<User> getFollowing() {
		return following;
	}

	public void setFollowing(List<User> following) {
		this.following = following;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Profile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(Profile userProfile) {
	this.userProfile = userProfile;
	}

	public Credentials getUserCreds() {
		return credentials;
	}
	
	public void setUserCreds(Credentials userCreds) {
		this.credentials = userCreds;
	}
	
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public List<Tweets> getUserTweets() {
		return userTweets;
	}

	public void setUserTweets(List<Tweets> userTweets) {
		this.userTweets = userTweets;
	}
}
