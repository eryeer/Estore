package com.mlb.estore.dao;

import java.util.List;

import com.mlb.estore.domain.Goods;

public interface GoodsDao {

	List<Goods> findAll();

	Long getTotalCount();

	List<Goods> findAll(int beginCount, int pageSize);

	Goods findGoodsById(String id);

}
