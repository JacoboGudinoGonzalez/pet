package com.mx.nj.pet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mx.nj.pet.model.Follow;

@Service("followService")
public interface FollowService {
	
	Follow addFollow(Follow follow);
	
	Follow getFollow(int userId, int followId);
	
	int[] getFollowCounters(int id);
	
	List<Follow> getMyFollows(int id);

	int deleteFollow(int userId, int id);
	
	List<Follow> getMyFollowed(int id);
}