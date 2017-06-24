package com.mlb.estore.web.frontservlet;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mlb.estore.service.OrderService;
import com.mlb.estore.utils.FactoryUtils;
import com.mlb.estore.utils.PaymentUtil;

public class PaymentServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public String confirm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获得 支付必须基本数据
		String orderid = request.getParameter("orderid");
		String money = request.getParameter("money");
		// 银行
		String pd_FrpId = request.getParameter("pd_FrpId");

		OrderService orderService = FactoryUtils
				.getInstance(OrderService.class);

		// 发给支付公司需要哪些数据
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString(
				"p1_MerId");
		String p2_Order = orderid;
		// 真实现金从数据库查找
		// String p3_Amt = orderService.findTotoalpriceByOid(orderid);
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString(
				"responseURL");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
		// 生成url --- url?
		request.setAttribute("pd_FrpId", pd_FrpId);
		request.setAttribute("p0_Cmd", p0_Cmd);
		request.setAttribute("p1_MerId", p1_MerId);
		request.setAttribute("p2_Order", p2_Order);
		request.setAttribute("p3_Amt", p3_Amt);
		request.setAttribute("p4_Cur", p4_Cur);
		request.setAttribute("p5_Pid", p5_Pid);
		request.setAttribute("p6_Pcat", p6_Pcat);
		request.setAttribute("p7_Pdesc", p7_Pdesc);
		request.setAttribute("p8_Url", p8_Url);
		request.setAttribute("p9_SAF", p9_SAF);
		request.setAttribute("pa_MP", pa_MP);
		request.setAttribute("pr_NeedResponse", pr_NeedResponse);
		request.setAttribute("hmac", hmac);

		return "/confirm.jsp";
	}

	public String callback(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获得回调所有数据
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");

		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 响应数据有效
			if (r9_BType.equals("1")) {
				OrderService orderService = FactoryUtils
						.getInstance(OrderService.class);
				orderService.orderFinish(r6_Order);
				// 浏览器重定向
				return "/srpay_success.jsp";
			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！");
				// 修改订单状态 为已付款
				// 回复支付公司
				response.getWriter().print("success");
			}
		} else {
			// 数据无效
			System.out.println("数据被篡改！");
		}
		return null;
	}

}
