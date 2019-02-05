package com.mx.nj.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.nj.pet.dao.FollowDAO;
import com.mx.nj.pet.model.Follow;
import com.mx.nj.pet.service.FollowService;

@Service("followService")
public class FollowServiceImpl implements FollowService {
	
	@Autowired FollowDAO followDao;

	@Transactional
	public Follow addFollow(Follow follow) {
		return followDao.addFollow(follow);
	}
	
	@Transactional
	public Follow getFollow(int userId, int followId){
		return followDao.getFollow(userId, followId);
	}
	
	@Transactional
	public int deleteFollow(int userId, int followId) {
		return followDao.deleteFollow(userId, followId);
	}
	
	@Transactional
	public int[] getFollowCounters(int id) {
		return followDao.getFollowCounters(id);
	}
	
	@Transactional
	public List<Follow> getMyFollows(int id) {
		return followDao.getMyFollows(id);
	}
	
	@Transactional
	public List<Follow> getMyFollowed(int id) {
		return followDao.getMyFollowed(id);
	}

}