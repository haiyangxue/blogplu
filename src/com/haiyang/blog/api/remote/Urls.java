package com.haiyang.blog.api.remote;


public class Urls {
	public final static String HOST = "121.250.220.182";//211.87.232.8 121.250.220.182
	public static final String BASIC_URL = "http://121.250.220.182:8080/First/action/json/";
	/**
	 * 版本更新地址
	 */
	public final static String UPDATE_VERSION ="";
	
	/**
	 * 登陆地址
	 */
	public static final String LOGIN_URL = BASIC_URL
			+ "user_login";
	/**
	 * 注册地址
	 */
	public static final String REGISTER_URL = BASIC_URL
			+ "user_register";
	/**
	 * 搜索列表
	 */
	public final static String SEARCH_LIST="";
	/**
	 * 用户个人信息
	 */
	public final static String USER_INFORMATION = "";
	
	/**
	 * 列表
	 */
	public final static String DYNASTY_LIST =BASIC_URL
			+ "dynasty_fetchAllDynasty";
	public final static String JOB_LIST = BASIC_URL
			+"job_fetchAllJob";
	public final static String EDUCATION_LIST =BASIC_URL
			+ "education_fetchAllEducation";
	public final static String EVENT_TYPE_LIST =BASIC_URL
			+ "eventtype_fetchAllType";
	public final static String EVENT_LIST =BASIC_URL
			+ "event_fetchAllEvent";
	/**
	 * 搜索列表
	 */
	public final static String EVENT_SEARCH =BASIC_URL
			+ "queryevents_fetchEventList";
	public final static String PEOPLE_SEARCH =BASIC_URL
			+ "peoplequery_fetch";
	public final static String PLACE_SEARCH =BASIC_URL
			+ "place_searchPlace";
	/**
	 * 添加
	 */
	public final static String EVENT_ADD =BASIC_URL
			+ "event_save";
	public final static String PEOPLE_ADD =BASIC_URL
			+ "people_save";
	public final static String PLACE_ADD =BASIC_URL
			+ "place_save";
	/**
	 * 按id查找指定
	 */
	public final static String EVENT_FETCH =BASIC_URL
			+ "event_fetch";
	public final static String PEOPLE_FETCH =BASIC_URL
			+ "people_fetch";
	public final static String PLACE_FETCH =BASIC_URL
			+ "place_fetch";
	
	//****************************************************************以下为未来实现的功能，后台还没有
	/**
	 * 用户收藏信息
	 */
	public final static String FAVORITE_LIST=BASIC_URL
			+"";
	
	/**
	 * 举报
	 */
	public final static String REPORT=BASIC_URL
			+"";
}
