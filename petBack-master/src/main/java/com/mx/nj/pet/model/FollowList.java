package com.mx.nj.pet.model;

import java.util.List;

public class FollowList extends Pagination{

	private List<?>following;
	private List<?>followed;
	
	public FollowList(){
		
	}

	public FollowList(List<?> following, List<?> followed) {
		super();
		this.following = following;
		this.followed = followed;
	}

	public List<?> getFollowing() {
		return following;
	}

	public void setFollowing(List<?> following) {
		this.following = following;
	}

	public List<?> getFollowed() {
		return followed;
	}

	public void setFollowed(List<?> followed) {
		this.followed = followed;
	}
	
	
}
