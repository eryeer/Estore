package com.mlb.estore.constants;

public interface ConstantInner {
	interface STATUS_ORDER {
		// 1.未付款
		int UNPAYED = 1;
		// 2.已付款
		int PAYED = 2;
		// 3.已过期
		int EXPIRED = 3;

	}

	interface STATUS_USER {
		// 1.未激活
		int INACTIVATED = 0;
		// 2.已激活
		int ACTIVATED = 1;
	}
}
