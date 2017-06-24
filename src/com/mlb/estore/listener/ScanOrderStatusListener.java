package com.mlb.estore.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mlb.estore.service.OrderService;
import com.mlb.estore.utils.FactoryUtils;
import com.mlb.estore.utils.ProxyUtils;

@WebListener
public class ScanOrderStatusListener implements ServletContextListener {
	private Timer timer;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		OrderService orderService = FactoryUtils
				.getInstance(OrderService.class);
		final OrderService proxyOrderService = new ProxyUtils<OrderService>(
				orderService).getProxy();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				System.out.println(sdf.format(new Date()) + "开始扫描过期账单");
				proxyOrderService.updateOrderExpire();
				System.out.println(sdf.format(new Date()) + "扫描完毕");
			}
		}, 10000, 600000);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timer.cancel();
		System.out.println(sdf.format(new Date()) + "扫描计时器停止");
	}

}
