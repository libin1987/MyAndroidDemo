package com.lib.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.service.LocationService;
import com.baidu.mapapi.SDKInitializer;
import com.lib.mydemo.model.LocCity;
import com.lib.mydemo.model.MyUser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;

import org.xutils.DbManager;
import org.xutils.DbManager.DaoConfig;
import org.xutils.x;

public class DemoApplication extends Application {
	private boolean loginFlag = false; // 未登录标记
	private MyUser user;

	public static String LOCATION_BCR = "location_bcr";
	// 百度地图
	private LocCity locCity = new LocCity();
	private LocCity sdlocCity = null;

	// 环信
	public static Context applicationContext;
	private static DemoApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";
	public LocationService locationService;
	private DaoConfig daoConfig;

	private boolean splash;
	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";

	// 各个平台的配置，建议放在全局Application或者程序入口
	{
		// 微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
		PlatformConfig.setWeixin("wx13f9dfd99ba34225", "c8fc465c50504c2cf9fa755e7781b579");
		// 豆瓣RENREN平台目前只能在服务器端配置
		// 新浪微博
		PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
		// 易信
		// PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
		PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
		// PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi",
		// "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
		// PlatformConfig.setAlipay("2015111700822536");
		// PlatformConfig.setLaiwang("laiwangd497e70d4",
		// "d497e70d4c3e4efeab1381476bac4c5e");
		// PlatformConfig.setPinterest("1439206");
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// xutils 初始化
		x.Ext.init(this);
		x.Ext.setDebug(true); // 是否输出debug日志

		// 百度初始化
		locationService = new LocationService(getApplicationContext());
		SDKInitializer.initialize(getApplicationContext());
		// 获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService.registerListener(mListener);
		// 注册监听
		locationService.setLocationOption(locationService.getDefaultLocationClientOption());

		// ImageLoader 初始化
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				// .showImageForEmptyUri(R.drawable.empty_photo)
				// .showImageOnFail(R.drawable.empty_photo)
				.cacheInMemory(true).cacheOnDisk(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				// .diskCacheSize(50 * 1024 * 1024)
				.diskCacheFileCount(100)// 缓存一百张图片
				// .writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);

		// 本地数据库
		setDaoConfig(new DbManager.DaoConfig().setDbName("jiabao")
				// .setDbDir(new File("/sdcard"))
				.setDbVersion(1).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
					@Override
					public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
						// TODO: ...
						// db.addColumn(...);
						// db.dropTable(...);
						// ...
					}
				}));
		// 环信
		applicationContext = this;
		instance = this;
	}

	public MyUser getUser() {
		return user;
	}

	public void setUser(MyUser user) {
		this.user = user;
	}

	public boolean isLogin() {
		return loginFlag;
	}

	public void setLogin(boolean loginflag) {
		this.loginFlag = loginflag;
	}

	public LocCity getLocCity() {
		return sdlocCity == null ? locCity : sdlocCity;
	}

	public void setLocCity(LocCity locCity) {
		this.locCity = locCity;
	}

	public LocCity getSdlocCity() {
		return sdlocCity;
	}

	public void setSdlocCity(LocCity sdlocCity) {
		this.sdlocCity = sdlocCity;
	}

	public static DemoApplication getInstance() {
		return instance;
	}

	public DaoConfig getDaoConfig() {
		return daoConfig;
	}

	public void setDaoConfig(DaoConfig daoConfig) {
		this.daoConfig = daoConfig;
	}

	public boolean isSplash() {
		return splash;
	}

	public void setSplash(boolean splash) {
		this.splash = splash;
	}

	// 百度定位广播
	/*****
	 * @see copy funtion to you project
	 *      定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
	 *
	 */
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				String city_name = location.getCity();
				locationService.stop(); // 停止定位服务
				Intent intent = new Intent(LOCATION_BCR);
				intent.putExtra("city_name", city_name);
				intent.putExtra("lat", location.getLatitude());
				intent.putExtra("lon", location.getLongitude());
				intent.putExtra("street", location.getStreet() + location.getStreetNumber());
				sendBroadcast(intent);

			}
		}

	};

}
