package com.haiyang.blog.api.remote;

import com.haiyang.blog.api.ApiHttpClient;
import com.haiyang.blog.util.TLog;
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
