package com.lib.http;

public class HttpAPI {
	public static final int CLIENT_USER=1;//1用户端
	public static final int CLIENT_WUYE=3;//3物业端
	public static final int CLIENT_HUISHOU=2;//2回收端
	public static final String APPDOWNLOAD = "";
	public static  int CLIENT_TYPE=CLIENT_USER;
	
	public static final boolean DEBUG = false;//测试模式 ：登录界面 三客户端登录入口
	public static final boolean DEV_ING = false;//开发中提示
	
	public static final String SERVER = "http://www.52jiabao.com/";
	//public static final String SERVER = "http://192.168.0.156:8080/recycle/";
	public static final String IMGSERVER = "http://img.52jiabao.com/";
	public static final String UPLOADFILE = SERVER+"api/upload/uploadimg";
	public static final String UPLOADFILES = SERVER+"api/upload/uploadimgs";
	public static final String SUBMITPLACE = null;

	public static final String SPEED_LOGIN = SERVER + "api/login2";
	public static final String LOGIN = SERVER + "api/login1";

	public static final String WEATHER="http://api.map.baidu.com/telematics/v3/weather";

}
