<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<%@include file="inc/common_head.jsp"%>
<script type="text/javascript">
	var existEmail = true;
	function checkUniqueEmail(value){
 		ajax({	
 			url: "${path}/userServlet?method=validEmail",
 			method: "get",
 			params: "email="+value,
 			callback: function(exist){
 				var username_notice = document.getElementById("username_notice");
 				if(exist == "true"){
 					username_notice.innerHTML = "×存在同名Email";
 					username_notice.style.color = "red";
 					existEmail = true;
 				} else {
 					username_notice.innerHTML = "√可以注册";
 					username_notice.style.color = "lime";
 					existEmail = false;
 				}
 			}
 		});
    }
    
    var result_captcha = false;
    function validCaptcha(value) {
    	ajax({
    		url:"${path}/userServlet?method=validCaptcha",
    		params:"captcha=" + value,
    		cache: false,
    		callback:function(data){
    			var captcha_notice = document.getElementById("captcha_notice");
    			//正确返回correct,错误返回wrong
    			if (data == "correct") {
					captcha_notice.innerHTML = "验证码正确";
 					captcha_notice.style.color = "lime";
 					result_captcha = true;
				} else if(data == "wrong"){
					captcha_notice.innerHTML = "验证码错误";
 					captcha_notice.style.color = "red";
 					result_captcha = false;
				} else {
					captcha_notice.innerHTML = "验证码失效";
 					captcha_notice.style.color = "red";
 					result_captcha = false;
				}
    		}
    	});
    }
</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>

	<div class="block block1">
		<div class="blank"></div>
		<div class="usBox">
			<div class="usBox_1">
				<div class="login_tab">
					<ul>
						<li onclick="location.href='login.jsp';">
							<a href="javascript:;">用户登录</a>
						</li>
						<li class="active">用户注册</li>
					</ul>
				</div>
				<form action="${path}/userServlet?method=register" method="post" name="formUser"
					onsubmit="return (!existEmail) && result_captcha && register();">
					<center><font color="red">${wrong_message}</font></center>
					<table width="100%" border="0" align="left" cellpadding="5"
						cellspacing="3">
						<tr>
							<td width="25%" align="right">账号</td>
							<td width="65%"><input name="email" type="text"
								id="username" onblur="if(is_registered(this.value)) {checkUniqueEmail(value);};"
								class="inputBg" /> <span id="username_notice"
								style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">昵称</td>
							<td><input name="nickname" type="text"
								id="nickname" onblur="check_nickname(this.value);"
								class="inputBg" /> <span id="nickname_notice"
								style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">密码</td>
							<td><input name="password" type="password" id="password1"
								onblur="check_password(this.value);"
								onkeyup="checkIntensity(this.value)" class="inputBg" />
								<span style="color:#FF0000"
								id="password_notice"> *</span></td>
						</tr>
						<tr>
							<td align="right">密码强度</td>
							<td>
								<table width="145" border="0" cellspacing="0" cellpadding="1">
									<tr align="center">
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_lower">弱</td>
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_middle">中</td>
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_high">强</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right">确认密码</td>
							<td><input name="confirm_password" type="password" 
								id="conform_password"
								onblur="check_conform_password(this.value);" class="inputBg" />
								<span style="color:#FF0000"
								id="conform_password_notice"> *</span></td>
						</tr>
						<tr>
							<td align="right">验证码</td>
							<td><input type="text" size="8" name="captcha" id="captcha"
								class="inputBg" onblur="if(check_captcha(this.value)){validCaptcha(value);};" /> <span style="color:#FF0000"
								id="captcha_notice"> *</span></td>
						</tr>
						<tr>
							<td align="right"></td>
							<td><img src="validatecode.jsp"
								style="vertical-align:middle;cursor:pointer;width:130px;height:35px;margin-top:-2px;"
								onClick="src='validatecode.jsp?'+Math.random()" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><label> <input name="agreement" type="checkbox"
									value="1" checked="checked" /> 我已看过并接受《<a
									href="javascript:;" style="color:blue" target="_blank">用户协议</a>》
							</label></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="left">
								<input type="submit" value="" class="us_Submit_reg">
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</table>
				</form>
				<div class="blank"></div>
			</div>
		</div>
	</div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>