package com.mlb.estore.dao;

import java.util.List;

import com.mlb.estore.domain.Cart;

public interface CartDao {

	Cart findCart(String uid, String gid);

	void add(String uid, String gid, int buynum);

	void update(String uid, String gid, int buynum);

	List<Cart> findAllByUid(String id);

	void deleteCart(String uid, String gid);

	Long getCartNumByUid(String uid);

	void deleteCarts(String uid);

}
