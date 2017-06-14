package com.mlb.estore.web.frontservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mlb.estore.domain.Goods;
import com.mlb.estore.domain.Pagination;
import com.mlb.estore.service.GoodsService;
import com.mlb.estore.utils.FactoryUtils;

public class GoodsServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String goodsList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String pageNum_str = request.getParameter("pageNum");
		String pageSize_str = request.getParameter("pageSize");
		Pagination<Goods> pagination = new Pagination<Goods>();
		GoodsService goodsService = FactoryUtils
				.getInstance(GoodsService.class);
		if (pageNum_str != null && pageSize_str != null) {
			int pageNum = Integer.parseInt(pageNum_str);
			int pageSize = Integer.parseInt(pageSize_str);
			pagination.setPageNum(pageNum);
			pagination.setPageSize(pageSize);
			pagination = goodsService.findAll(pagination);
		} else {
			pagination = goodsService.findAll(pagination);
		}
		request.setAttribute("pagination", pagination);
		return "/goods.jsp";
	}

	public String goodsDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		GoodsService goodsService = FactoryUtils
				.getInstance(GoodsService.class);
		Goods goods = goodsService.findGoodsById(id);
		request.setAttribute("goods", goods);
		return "/goods_detail.jsp";
	}
}
