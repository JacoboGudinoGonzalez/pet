package com.mx.nj.pet.dao;

import java.util.List;

import com.mx.nj.pet.model.Follow;

public interface FollowDAO {

	Follow addFollow(Follow follow);
	
	Follow getFollow(int userId, int followId);
	
	int[] getFollowCounters(int id);
	
	public List<Follow> getMyFollows(int id);

	int deleteFollow(int userId, int followId);

	List<Follow> getMyFollowed(int idParam);
}