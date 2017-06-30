package com.cooksy.twitter.pojo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table (name="tweets")
public class Tweets {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private User author;
	
	@Version
	private Timestamp timestamp;
	

	private String content;
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	
	@ManyToMany
	private List<User> usersliked;

	
	@ManyToOne
	private Tweets inReplyto;
	
	@ManyToOne
	private Tweets repostOf;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Timestamp getTimestamp() {
		return timestamp;
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
		Tweets other = (Tweets) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Tweets getInReplyto() {
		return inReplyto;
	}
	public void setInReplyto(Tweets inReplyto) {
		this.inReplyto = inReplyto;
	}
	public Tweets getRepostOf() {
		return repostOf;
	}
	public void setRepostOf(Tweets repostOf) {
		this.repostOf = repostOf;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	private Boolean isDeleted=false;
	
	public List<User> getUsersliked() {
		return usersliked;
	}
	public void setUsersliked(List<User> usersliked) {
		this.usersliked = usersliked;
	}

}
