package com.liushu.utils;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtil {
	
	public static final String TPL_SUFFIX = ".html";

	public static String articleTemplateReal = "WEB-INF/view/admin/articleTemplate";
	
	public static String channelTemplateReal = "WEB-INF/view/admin/channelTemplate";
	
	public static final String ENCODING = "UTF-8";
	
	private static final Whitelist user_content_filter = Whitelist.relaxed();
	static int totalFolder;
    static int totalFile;
 
    static
    {
      user_content_filter.addTags(new String[] { "embed", "object", "param", "span", "div", 
       "font" });
      user_content_filter.addAttributes(":all", new String[] { "style", "class", "id", 
       "name" });
      user_content_filter.addAttributes("object", new String[] { "width", "height", 
       "classid", "codebase" });
      user_content_filter.addAttributes("param", new String[] { "name", "value" });
      user_content_filter.addAttributes("embed", new String[] { "src", "quality", "width", 
        "height", "allowFullScreen", "allowScriptAccess", "flashvars", 
        "name", "type", "pluginspage" });
 
      totalFolder = 0;
      totalFile = 0;
    }	
	
	//获取安全的输入参数
	public static String filterHTML(String content) {
	     //Whitelist whiteList = new Whitelist();
	     String s = Jsoup.clean(content, user_content_filter);
	     return s;
	}
	
	/**
	 * 功能描述:处理json数据
	 * @param
	 * @param response
	 */
	public static void toJson(ModelMap model,HttpServletResponse response){
		//fastjson 序列化
		response.setContentType("text/html;charset=UTF-8");
    	String str=JSON.toJSONString(model); 
    	try {
			response.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 功能描述:处理json数据
	 * @param
	 * @param response
	 */
	public static void toJson(String str,HttpServletResponse response){
		//fastjson 序列化
		response.setContentType("text/html;charset=UTF-8");
    	String str1=JSON.toJSONString(str); 
    	try {
			response.getWriter().write(str1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 判断变量是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断上传的文件是否是图片格式
	 * @param str
	 * @return
	 */
	public static boolean isImage(String str) {
		String[] strs = { "png", "jpg", "jpeg", "gif", "bmp" };
		for (String s : strs) {
			if (s.equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 对象转换为字符串
	 * @param s
	 * @return
	 */
	 public static String null2String(Object s) {
	    return s == null ? "" : s.toString().trim();
	 }
	 
	 /**
	  * 整型转换成非空的整型
	  * @param i
	  * @return
	  */
	 public static Integer integer2NotNullInteger(Integer i)
	 {	 
		 return i == null ? 0 : i; 
	 }
	 
	 public static Double double2NotNullDouble(Double i)
	 {	 
		 return i == null ? 0.0d : i; 
	 }
	 
	 public static String getIpAddr(HttpServletRequest request){  
			String ipAddress = request.getHeader("x-forwarded-for");  
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
				ipAddress = request.getHeader("Proxy-Client-IP");  
			}  
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
				ipAddress = request.getHeader("WL-Proxy-Client-IP");  
			}  
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
				ipAddress = request.getRemoteAddr();  
				if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
					//根据网卡取本机配置的IP  
					InetAddress inet=null;  
					try {  
						inet = InetAddress.getLocalHost();  
					} catch (UnknownHostException e) {  
						e.printStackTrace();  
					}  
					ipAddress= inet.getHostAddress();  
				}  
			}  
			//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
			if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
				if(ipAddress.indexOf(",")>0){  
					ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
				}  
			}  
			return ipAddress;   
		}
	 
	 
		/**
		 * 用来去掉List中空值和相同项的。
		 * 
		 * @param list
		 * @return
		 */
		public static List<String> removeSameItem(List<String> list) {
			List<String> difList = new ArrayList<String>();
			for (String t : list) {
				if (t != null && !difList.contains(t)) {
					difList.add(t);
				}
			}
			return difList;
		}
		
		
		 public static String sendPost(String url, String param) {
		        PrintWriter out = null;
		        BufferedReader in = null;
		        String result = "";
		        try {
		            URL realUrl = new URL(url);
		            // 打开和URL之间的连接
		            URLConnection conn = realUrl.openConnection();
		            // 设置通用的请求属性
		            conn.setRequestProperty("accept", "*/*");
		            conn.setRequestProperty("connection", "Keep-Alive");
		            conn.setRequestProperty("user-agent",
		                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		            // 发送POST请求必须设置如下两行
		            conn.setDoOutput(true);
		            conn.setDoInput(true);
		            // 获取URLConnection对象对应的输出流
		            out = new PrintWriter(conn.getOutputStream());
		            // 发送请求参数
		            out.print(param);
		            // flush输出流的缓冲
		            out.flush();
		            // 定义BufferedReader输入流来读取URL的响应
		            in = new BufferedReader(
		                    new InputStreamReader(conn.getInputStream()));
		            String line;
		            while ((line = in.readLine()) != null) {
		                result += line;
		            }
		        } catch (Exception e) {
		            //System.out.println("发送 POST 请求出现异常！"+e);
		            e.printStackTrace();
		        }
		        //使用finally块来关闭输出流、输入流
		        finally{
		            try{
		                if(out!=null){
		                    out.close();
		                }
		                if(in!=null){
		                    in.close();
		                }
		            }
		            catch(IOException ex){
		                ex.printStackTrace();
		            }
		        }
		        return result;
		    }  
		 
		 public static String getRandomCode(){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String code = sdf.format(date);
			int a = (int)((Math.random()*9+1)*1000);
			return code+a;
		 }
	/**
	 * 校验字符串长度,不超出返回原字符创,超出长度则截取指定长度返回
	 * 
	 * @param
	 * @return
	 */

	public static String checkoutAndCut(String string ,int length) {
		if(string.length()<=length){
			return string;
		}else{
			return string.substring(0,length);
		}
	}

	/**
	 * 获取uuid
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 通过token获取用户id
	 * @param token
	 * @return
	 */
	public static String getUserId(String token){
		return token.substring(token.lastIndexOf("_")+1);
	}

	/**
	 * 通过map获取用户id
	 * @param params
	 * @return
	 */
	public static String getUserId(Map<String,Object> params){
		String token = params.get("liushuUserToken").toString();
		return token.substring(token.lastIndexOf("_")+1);
	}

	public static String getDefaultNickname(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String code = sdf.format(date);
		String nickname = "书友"+code;
		return nickname;
	}
}
