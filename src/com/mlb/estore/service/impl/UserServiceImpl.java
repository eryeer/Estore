package com.mlb.estore.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlb.estore.dao.UserDao;
import com.mlb.estore.domain.User;
import com.mlb.estore.service.UserService;
import com.mlb.estore.utils.FactoryUtils;

public class UserServiceImpl implements UserService {
	UserDao userDao = FactoryUtils.getInstance(UserDao.class);
	final static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Override
	public boolean findEmail(String email) {

		return userDao.findEmail(email);
	}

	@Override
	public boolean register(User user) {

		return userDao.register(user);
	}

	@Override
	public boolean activateUser(String id, String activecode) {

		return userDao.activateUser(id, activecode);
	}

	@Override
	public void deleteUser(String id) {
		userDao.deleteUser(id);
	}

	@Override
	public User findUserByIdAndActivecode(String id, String activecode) {

		return userDao.findUserByIdAndActivecode(id, activecode);
	}

	@Override
	public User findUser(User user) {

		return userDao.findUser(user);
	}

}
