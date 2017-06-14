package com.mlb.estore.service;

import com.mlb.estore.domain.Goods;
import com.mlb.estore.domain.Pagination;

public interface GoodsService {

	Pagination<Goods> findAll(Pagination<Goods> pagination);

	Goods findGoodsById(String id);

}
