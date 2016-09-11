/**
 * @author dawson dong
 */

package com.lib.utility;

import org.xutils.x;

import com.lib.android.common.util.ToastUtils;
import com.lib.http.HttpAPI;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class ActivityUtil {

	public static void chooseImage(Activity activity, String title, int requestCode) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_PICK);
		Intent chooser = Intent.createChooser(intent, title);
		startActivityForResult(activity, chooser, requestCode);
	}

	public static boolean startActivity(Context context, Class<?> clazz) {
		if (context == null || clazz == null) {
			return false;
		}
		Intent intent = new Intent(context, clazz);
		return startActivity(context, intent);
	}

	public static boolean startActivity(Class<?> clazz) {
		return startActivity(x.app(), clazz);
	}

	public static boolean startActivity(Intent intent) {
		return startActivity(x.app(), intent);
	}

	public static boolean startActivity(Context context, Intent intent) {
		if (context == null || intent == null) {
			return false;
		}

		if (!(context instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}

//		try {
			context.startActivity(intent);
//		} catch (Exception globalException) {
//			// catch all exception here
//			globalException.printStackTrace();
//			return false;
//		}
		return true;
	}

	public static boolean startActivityForResult(Activity activity, Intent intent, int requestCode) {
		if (activity == null || intent == null) {
			return false;
		}

		try {
			activity.startActivityForResult(intent, requestCode);
		} catch (Exception globalException) {
			globalException.printStackTrace();
			return false;
		}
		return true;
	}

	public static void startURL(String url) {
		 Uri uri = Uri.parse(url);  
		 startActivity(new Intent(Intent.ACTION_VIEW,uri));  
	}
	public static void startTel(String tel) {
		 Uri uri = Uri.parse("tel:"+tel);  
		startActivity(new Intent(Intent.ACTION_DIAL,uri));  
	}
	public static void startSMS(String tel) {
		Uri uri = Uri.parse("smsto:"+tel);  
		startActivity(new Intent(Intent.ACTION_SENDTO,uri));  
	}

	public static void startSingleImageActivity(Activity activity, int requestImage) {
		Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
		// 是否显示拍摄图片
		intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
		// 最大可选择图片数量
		// intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,
		// maxNum);
		// 选择模式(单张或者多张)
		intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
		activity.startActivityForResult(intent, requestImage);
	}

	public static void startDev_Activity(Class<?> clazz) {
		if (HttpAPI.DEV_ING) {
			ToastUtils.show(x.app(), "开发中...");
		}
		else {
			ActivityUtil.startActivity(clazz);
		}
	}

//	public static boolean isLogin(UserApplication userApplication, Activity activity) {
//		// TODO Auto-generated method stub
//		if (!userApplication.isnLogin()) {
//			activity.finish();
//			ToastUtils.show(activity, "你还没登录，不能执行此操作");
//			Intent intent = new Intent(activity, LoginActivity.class);
//			startActivity(intent);
//			return false;
//		}
//		else {
//			return true;
//		}
//	}
}
