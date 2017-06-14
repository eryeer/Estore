package com.mlb.estore.dao;

import com.mlb.estore.domain.User;

public interface UserDao {

	boolean findEmail(String email);

	boolean register(User user);

	boolean activateUser(String id, String activecode);

	void deleteUser(String id);

	User findUserByIdAndActivecode(String id, String activecode);

	User findUser(User user);

}
