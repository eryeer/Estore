package com.mlb.estore.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlb.estore.dao.AdminDao;
import com.mlb.estore.domain.Goods;
import com.mlb.estore.service.AdminService;
import com.mlb.estore.utils.FactoryUtils;

public class AdminServiceImpl implements AdminService {
	AdminDao adminDao = FactoryUtils.getInstance(AdminDao.class);
	final static Logger logger = LogManager.getLogger(AdminServiceImpl.class);

	@Override
	public void addGoods(Goods goods) {
		adminDao.addGoods(goods);
	}

}
