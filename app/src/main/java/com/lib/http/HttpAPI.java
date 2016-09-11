package com.lib.http;

import java.io.UnsupportedEncodingException;

import android.util.Base64;

public class HttpAPI {
	public static final int CLIENT_USER=1;//1用户端
	public static final int CLIENT_WUYE=3;//3物业端
	public static final int CLIENT_HUISHOU=2;//2回收端
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
	//登录验证码
	public static final String SENDSMS_FOR_LOGIN = SERVER + 
			"api/sendsms_for_login";
	//注册验证码
	public static final String SENDSMS_FOR_REGISTER = SERVER + 
			"api/sendsms_for_regist";
	//注册
		public static final String REGISTER = SERVER + "api/regist";
		
	//绑定手机验证码
		public static final String SENDSMS_FOR_BIND = SERVER + 
				"api/user/sendsms_for_binding_mobile";
	//绑定手机
		public static final String BIND = SERVER + "api/user/binding_mobile";	
	
	//忘记密码验证码
	public static final String SENDSMS_FOR_FORGET_PSSSWORD = SERVER+
			"api/sendsms_for_forget_password";
	//重置密码
		public static final String RESET_PSSSWORD = SERVER+
				"api/reset_password";
	//修改手机号验证码
		public static final String SENDSMS_FOR_CHANGE_MOBILE = SERVER+
				"api/user/sendsms_for_change_mobile";
	//修改手机号
			public static final String CHANGE_MOBILE = SERVER+
						"api/user/change_mobile";
	//修改真实姓名
			public static final String CHANGE_REAL_NAME = SERVER+
						"api/user/change_real_name";
	//修改支付密码
			public static final String CHANGE_PAY_PWD = SERVER+
					"api/user/change_pay_pwd";
	//查看个人资料
			public static final String USER_DATA = SERVER+
					"api/user/userdata";
	//保存新增地址
			public static final String ADDRESS_DOSAVE = SERVER+
					"api/address/dosave";			
	//我的地址
			public static final String MY_ADDRESS = SERVER+
					"api/address/myadd";
	//登出
			public static final String LOGIN_OUT = SERVER+
					"api/user/logout";		
			
	//我要买列表
			public static final String IBUYSELL_LIST = SERVER+
					"api/buysell/list";	
	//我要买搜索
			public static final String IBUY_SEARCH = SERVER+
					"api/buysell/search";	

			
			public static String BUYSELL_DETAIL=SERVER+"api/buysell/detail";
		
	//我要卖列表
//			public static final String ISELL_LIST = SERVER+
//					"api/secondhand/buy/list";	
	//我要卖搜索
			public static final String ISELL_SEARCH = SERVER+
					"api/secondhand/buy/ss";			

			
			public static String isell_details(long sellId){
				return SERVER+"api/secondhand/buy/"+sellId+"/detail";				
			}
			
	//买卖信息类别
			public static final String SECOND_HAND_TYPE = SERVER+"api/dict/list";
			
			public static final String SALE_TYPE = 
					SERVER+"api/secondhand/sell/grouping";		
	//发布出售信息
		public static final String RELEASE_SELL = 
						SERVER+"api/buysell/add";
	//发布求购信息
		public static final String RELEASE_BUY = 
						SERVER+"api/buysell/add";
		
	//获取某个城市下的区县
		public static final String AREAS_CHOOSE = 
				SERVER+"api/cfg/common/country/county_by_city";
	//认证
//		public static final String PROBATE= 
//						SERVER+"api/movehome/probate";

	// 爱心公益列表
	public static final String LOVE_HEART_LIST = SERVER + 
			"api/commonweal/list";
	// 爱心公益详情
	public static final String LOVE_HEART_DETAILS = SERVER + 
			"api/commonweal/{commonwealId}/detail";
	
	//我的积分
	public static final String MY_SCORE = SERVER+
			"api/user_score/mylog";
	
	//我的积分余额
	public static final String BALANCE_ALL = SERVER+
			"api/balance/all";
	
	//提现记录
	public static final String SCORE_WITHDRAW = SERVER+
			"api/user_score/extract_record";
	//总额
	public static final String ALL_SCORE = SERVER+
			"api/balance/add";
	
	//搬家服务列表
	public static final String HOME_LIST = SERVER+
			"api/home/list";
	//搬家服务详情
	public static final String MOVE_HOME_DETAILS = SERVER + 
			"api/movehome/{movehomeId}/detail";
	//发布搬家
			public static final String RELEASE_MOVE_HOME = SERVER+
					"api/movehome/dosave";
	//发布搬家判断
			public static final String HOME_ISFIRM = SERVER+
					"api/home/isFirm";		
			
	//发布搬家服务类别
//		public static final String SERVICE_TYPE = SERVER+
//				"api/movehome/type/list";

	//添加收藏
		public static final String ADD_FAV = SERVER+
				"api/collect/add";
		
	public static final String USERDATA = SERVER+"api/user/userdata";

	public static final String CHANGE_LOGIN_PWD = SERVER+
			"api/user/change_login_pwd";

	public static final String CHANGE_HEAD_IMG = SERVER+"api/user/change_head_img";

	public static final String LOGOUT = SERVER+"api/user/logout";
	
	//员工列表
	public static final String RECYCLE_EMPLOYEE_LIST = 
			SERVER+"api/employee/list";
	//轮播图
	public static final String SLIDE_PIC = 
			SERVER+"api/common/slideshow/list";
	//便民服务
	public static final String CONVENIENCE_SERVICE = 
			SERVER+"api/life_apply/list";
	
	//关于我们，常见问题系统消息
	public static final String SUPPORT = 
				SERVER+"api/support/list";
	
	//积分商城
	public static final String SCORE_SHOP = 
			SERVER+"api/score_shop/list";
	//分类回收
	public static final String WEEK_LIST = 
			SERVER+"api/retime/weeklist";
	//分类回收详情
	public static final String RETIME_GOODS_DETAIL = 
			SERVER+"api/retime/goods/detail";
	//分类回收详情
	public static String retime_goods_details(long scoreshopId) {
		return SERVER + "api/retime/goods"+scoreshopId+"/detail";
	}
	//积分商城详情
	public static String score_shop_details(long scoreshopId) {
		return SERVER + "api/score_shop/"+scoreshopId+"/detail";
	}
	
	//
	public static final String MY_SCORESS = 
			SERVER+"api/order/list";
	//订单列表
	public static final String ORDER_LIST = 
			SERVER+"api/order/list";
	//订单详情
	public static final String ORDER_DETAILS = 
			SERVER+"api/order/info";
	//删除订单
	public static final String ORDER_DEL = 
			SERVER+"api/order/del";
	public static final String ORDER_CANCEL = 
			SERVER+"api/order/cancel";
	//废品回收距离
	public static final String RECYCLING_LIST = 
			SERVER+"api/range/list";
	//积分充值
	public static final String SCORE_PAY = 
			SERVER+"/api/balance/pay";
	//订单支付成功通知
		public static final String ORDER_NOTIFY = 
				SERVER+"api/balance/notify";
	
	
	public static final String AP = "zLj3dDzzG7t3oU0Zi0tYwj8K";//百度ap
	public static final String BIG_GOODS_LIST = SERVER+"api/big_goods/list";//大件上门物品清单
	public static final String BIG_GOODS_BESPEAK = SERVER+"api/big_goods/bespeak";//大件上门 预约上门
	public static final String MONTHLIST = SERVER+"api/retime/monthlist";//按月取回垃圾分类信息
	public static final String TODAYLIST = SERVER+"api/retime/todaylist";//今日回收垃圾分类
	public static final String ORDER_CREATE = SERVER+"api/order/create";//生成订单-物业扫业主
	public static final String APPDOWNLOAD = "http://www.52jiabao.com/resources/jiabao/index.html";//下载app地址
	public static final String MYSTOCK = SERVER+"api/order/stock";//物业库存
	public static final String APPLY = SERVER+"api/order/apply";
	public static final String ORDERLIST = SERVER+"api/order/list";
	public static final String EMPLOYEE_ADD = SERVER+"api/employee/add";
	public static final String ORDER_UPDATE = SERVER+"api/order/update";
	public static final String ORDER_CHECK = SERVER+"api/order/check";
	public static final String ORDER_CONFIRM = SERVER+"api/order/confirm";
	public static final String EMPLOYEE_DEL = SERVER+"api/employee/del";
	public static final String ORDER_BIGS = SERVER+"api/order/bigs";
	public static final String DICT_LIST = SERVER+"api/dict/list";
	public static final String ORDER_PAY = SERVER+"api/order/pay";
	public static final String GOODSSEARCH = SERVER+"api/retime/goodsSearch";

	public static final String USER_SCORE_EXTRACT = SERVER+"api/user_score/extract";
	public static final String BUYSELL_HOME = SERVER+"api/buysell/home";
	public static final String PRAISE_ADD = SERVER+"api/praise/add";
	public static final String COMMENT_ADD = SERVER+"api/tcomment/add";
	public static final String STREETLIST = SERVER+"api/user/streetlist";
	public static final String ORDER_STOCKALL=SERVER+"api/order/stockall";
	public static final String ORDER_STOCKPAY = SERVER+"api/order/stockpay";
	public static final String ORDER_APPLYRECYCLE = SERVER+"api/order/applyRecycle";
	public static final String USER_CHANGE = SERVER+"api/user/change";
	public static final String USER_SET_PWD = SERVER+"api/user/set_pwd";
	public static final String ATTESTATION_ADD = SERVER+"api/attestation/add";
	public static final String COMMENT_LIST = SERVER+"api/tcomment/list";
	public static final String SUPPORT_UNREAD = SERVER+"api/support/unRead";
	public static final String ORDER_DOSCORE = SERVER+"api/order/doscore";
	public static final String HOME_DETAIL = SERVER+"api/home/detail";
	public static final String HOME_ADDCOUNT = SERVER+"api/home/addCount";
	public static final String HOME_DEL = SERVER+"api/home/del";
	public static final String BUYSELL_DEL = SERVER+"api/buysell/del";
	public static final String HOME_ADD = SERVER+"api/home/add";
	public static final String ORDER_ISFINISH = SERVER+"api/order/isfinish";
	
	
	public static String support_details(long supportId) {
		return SERVER + "api/support/"+supportId+"/detail";
	}
	public static String love_heart_details(long commonwealId) {
		return SERVER + "api/commonweal/"+commonwealId+"/detail";
	}
	public static String move_home_details(long movehomeId) {
		return SERVER + "api/movehome/"+movehomeId+"/detail";
	}
	
	public static String decryptBASE64(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		try {
			byte[] encode = str.getBytes("UTF-8");
			// base64 解密
			return new String(Base64.decode(encode, 0, encode.length, Base64.DEFAULT), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String get_commonweal_detail(long id) {
		return SERVER+"/api/commonweal/"+id+"/detail";
	}
	

	public static String commonweal_enter(long id) {
		return SERVER+"/api/commonweal/"+id+"/enter";
	}
	public static String getAddrDetail(long id) {
		return SERVER+"/api/address/"+id+"/detail";
	}
	public static String del_address(long id) {
		return SERVER+"/api/address/"+id+"/del";
	}
}
