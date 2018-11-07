package com.liushu.intercetpor;

import com.alibaba.fastjson.JSON;
import com.liushu.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AddIntercetpor implements HandlerInterceptor {

	@Autowired
	private RestTemplate restTemplate;
	@Override
	public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		String token = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("liushuUserToken")){
					token = cookie.getValue();
				}
			}
		}
		if(StringUtils.isBlank(token)){
			token=request.getParameter("liushuUserToken");
		}
		String uri = request.getRequestURI().toString();
		uri = "/activemq"+uri;
		System.out.println(uri);
		String method = request.getMethod();
		if(StringUtils.isNotBlank(token)){
			Result result  = restTemplate.getForEntity("http://liushu-zuul/sso/v1/user/login/check?token="+token+"&requestUrl="+uri+"&method="+method,Result.class).getBody();
			if("0".equals(result.getCode())){
				return true;
			}else{
				PrintWriter writer = response.getWriter();
				writer.write(JSON.toJSONString(result));
				writer.flush();
				writer.close();
				return false;
			}
		}else{
			Result result = new Result();
			result.setMsg("未登录");
			result.setCode("-998");
			PrintWriter writer = response.getWriter();
			writer.write(JSON.toJSONString(result));
			writer.flush();
			writer.close();
			return false;
		}
//		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
