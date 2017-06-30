package com.cooksy.twitter.pojo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Credentials {
	private String username;
	private String password;
	
	@Column(unique=true)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
