package com.mlb.estore.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlb.estore.dao.GoodsDao;
import com.mlb.estore.domain.Goods;
import com.mlb.estore.domain.Pagination;
import com.mlb.estore.service.GoodsService;
import com.mlb.estore.utils.FactoryUtils;

public class GoodsServiceImpl implements GoodsService {
	GoodsDao goodsDao = FactoryUtils.getInstance(GoodsDao.class);
	final static Logger logger = LogManager.getLogger(GoodsServiceImpl.class);

	@Override
	public Pagination<Goods> findAll(Pagination<Goods> pagination) {
		List<Goods> data = goodsDao.findAll(pagination.getBeginCount(),
				pagination.getPageSize());
		Long totalCount = goodsDao.getTotalCount();
		pagination.setData(data);
		pagination.setTotalCount(totalCount);

		return pagination;
	}

	@Override
	public Goods findGoodsById(String id) {

		return goodsDao.findGoodsById(id);
	}

	@Override
	public List<Goods> getRanking() {

		return goodsDao.getRanking();
	}

}
