package com.mlb.estore.service;

import java.util.List;

import com.mlb.estore.domain.Cart;

public interface CartService {

	boolean addToCart(String uid, String gid, int buynum);

	List<Cart> findAllByUid(String id);

	void deleteCart(String uid, String gid);

	Long getCartNumByUid(String uid);

}
