package com.mlb.estore.service.impl;

import java.util.List;

import com.mlb.estore.dao.CartDao;
import com.mlb.estore.dao.GoodsDao;
import com.mlb.estore.domain.Cart;
import com.mlb.estore.domain.Goods;
import com.mlb.estore.service.CartService;
import com.mlb.estore.utils.FactoryUtils;

public class CartServiceImpl implements CartService {
	CartDao cartDao = FactoryUtils.getInstance(CartDao.class);

	@Override
	public void addToCart(String uid, String gid, int buynum) {
		Cart cart = cartDao.findCart(uid, gid);
		if (cart == null) {
			cartDao.add(uid, gid, buynum);
		} else {
			buynum += cart.getBuynum();
			cartDao.update(uid, gid, buynum);
		}
	}

	@Override
	public List<Cart> findAllByUid(String id) {
		List<Cart> list = cartDao.findAllByUid(id);
		GoodsDao goodsDao = FactoryUtils.getInstance(GoodsDao.class);
		for (Cart cart : list) {
			Goods goods = goodsDao.findGoodsById(cart.getGid());
			cart.setGoods(goods);
		}
		return list;
	}

	@Override
	public void deleteCart(String uid, String gid) {
		cartDao.deleteCart(uid, gid);
	}

}
