package com.mlb.estore.service;

import java.util.List;

import com.mlb.estore.domain.Goods;
import com.mlb.estore.domain.Pagination;

public interface GoodsService {

	Pagination<Goods> findAll(Pagination<Goods> pagination);

	Goods findGoodsById(String id);

	List<Goods> getRanking();

}
