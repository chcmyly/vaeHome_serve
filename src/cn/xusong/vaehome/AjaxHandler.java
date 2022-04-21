package cn.xusong.vaehome;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

//网络拦截器
public class AjaxHandler extends Handler{

	@Override
	public void handle(String url, HttpServletRequest request, HttpServletResponse response, boolean[] bool) {
		response.addHeader("Access-Control-Allow-Origin", "*");//允许Ajax发送跨域请求
		nextHandler.handle(url, request, response, bool);
		
	}

}
