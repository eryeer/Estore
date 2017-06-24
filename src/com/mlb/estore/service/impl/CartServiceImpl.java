package com.mlb.estore.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlb.estore.dao.CartDao;
import com.mlb.estore.dao.GoodsDao;
import com.mlb.estore.domain.Cart;
import com.mlb.estore.domain.Goods;
import com.mlb.estore.service.CartService;
import com.mlb.estore.utils.FactoryUtils;

public class CartServiceImpl implements CartService {
	CartDao cartDao = FactoryUtils.getInstance(CartDao.class);
	final static Logger logger = LogManager.getLogger(CartServiceImpl.class);

	@Override
	public boolean addToCart(String uid, String gid, int buynum) {
		GoodsDao goodsDao = FactoryUtils.getInstance(GoodsDao.class);
		int num = goodsDao.findNumById(gid);
		if (num < buynum) {
			return false;
		}
		Cart cart = cartDao.findCart(uid, gid);
		if (cart == null) {
			cartDao.add(uid, gid, buynum);
		} else {
			buynum += cart.getBuynum();
			cartDao.update(uid, gid, buynum);
		}
		goodsDao.updateNumById(gid, (num - buynum));
		return true;
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

	@Override
	public Long getCartNumByUid(String uid) {

		return cartDao.getCartNumByUid(uid);
	}

}
