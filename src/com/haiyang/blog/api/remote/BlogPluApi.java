package com.haiyang.blog.api.remote;


import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HttpContext;
import org.kymjs.kjframe.http.HttpConfig;

import com.haiyang.blog.AppConfig;
import com.haiyang.blog.AppContext;
import com.haiyang.blog.api.ApiHttpClient;
import com.haiyang.blog.util.TLog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class BlogPluApi {

    /**
     * 登陆
     * 
     * @param username
     * @param password
     * @param handler
     */
	
    public static void login(String username, String password,
            AsyncHttpResponseHandler handler) {
    	TLog.log("Login", "handlelogin");
        RequestParams params = new RequestParams();
        params.put("user.user_name", username);
        params.put("user.user_pass", password);
        TLog.log("login","yes");
        String loginurl = Urls.LOGIN_URL;
        ApiHttpClient.post(loginurl, params, handler);
    }
  
   
}
