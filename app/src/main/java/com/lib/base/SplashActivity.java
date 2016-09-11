package com.lib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.lib.android.common.util.PreferencesUtils;
import com.lib.android.common.util.StringUtils;
import com.lib.http.HttpAPI;
import com.lib.http.HttpLib;
import com.lib.http.HttpLib.RequestListener;
import com.lib.mydemo.LoginActivity;
import com.lib.mydemo.MainActivity;
import com.lib.mydemo.R;
import com.lib.mydemo.model.MyUser;
import com.lib.utility.ActivityUtil;

import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 
 * @{#
 * 
 * class desc: 启动画面 (1)判断是否是首次加载应用--采取读取SharedPreferences的方法
 * (2)是，则进入GuideActivity；否，则进入MainActivity (3)3s后执行(2)操作
 * 
 * <p>
 * Copyright: Copyright(c) 2016
 * </p>
 * 
 * @Version 1.0
 * @Author <a href="mailto:">libin</a>
 * 
 * 
 */
public class SplashActivity extends Activity {
	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	// 延迟3秒
	private static final long SPLASH_DELAY_MILLIS = 1000;

	/**
	 * Handler:跳转到不同界面
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;
			}
			super.handleMessage(msg);

		}
	};

	private DemoApplication demoApplication = DemoApplication.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
	
		doLogin();
	}

	private void init() {
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (Util.contatins(this, Util.FILE_NAME, Util.getAppVersionCode(this) + "")) {
			// 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
			if (!demoApplication.isSplash()) {
				mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
				demoApplication.setSplash(true);
			} else {
				goHome();
			}

		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}
	}

	protected void doLogin() {
		if (demoApplication.isLogin()) {
			init();
			return;
		}
		String mobile = PreferencesUtils.getString(x.app(), LoginActivity.MOBILE);
		String password = PreferencesUtils.getString(x.app(), LoginActivity.PASSWORD);
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password)) {
			init();
			return;
		}
		RequestParams logrequestParams = new RequestParams(HttpAPI.LOGIN);
		logrequestParams.addBodyParameter("account", mobile);
		logrequestParams.addBodyParameter("pwd", password);
		logrequestParams.addBodyParameter("type", HttpAPI.CLIENT_TYPE + "");
		
		
		HttpLib.get_with_nocheck(logrequestParams, new RequestListener() {

			@Override
			public void onSuccess(String result) {
				MyUser myUser = JSON.parseObject(result, MyUser.class);
				DemoApplication demoApplication = DemoApplication.getInstance();
				demoApplication.setLogin(true);
				demoApplication.setUser(myUser);
				init();
			}

			@Override
			public void onError(String error) {
				init();
			}
		});

	}

	private void goHome() {
		ActivityUtil.startActivity(MainActivity.class);
		finish();
	}

	private void goGuide() {
		Intent intent = new Intent(SplashActivity.this, SplashIntro.class);
		startActivity(intent);
		finish();
	}
}
