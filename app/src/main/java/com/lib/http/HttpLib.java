package com.lib.http;

import android.app.Activity;
import android.app.Dialog;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lib.android.common.util.PreferencesUtils;
import com.lib.android.common.util.StringUtils;
import com.lib.base.DemoApplication;
import com.lib.mydemo.LoginActivity;
import com.lib.mydemo.model.MyUser;
import com.lib.utility.ActivityUtil;
import com.lib.utility.BitmapHelper;
import com.lib.utility.CustomProgressDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

public class HttpLib {
	public interface RequestListener {
		public void onSuccess(String result);

		public void onError(String error);
	}

	public interface UploadListener {
		public void onSuccess(String result);

		public void onError(String error);
	}

	public interface UploadMultiListener {
		public void onSuccess(String result);

		public void onError(String error);
	}

	public static void get_with_nocheck(final RequestParams requestParams, final RequestListener callBack) {
		x.http().get(requestParams, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if (!checkResult(result, requestParams, callBack,false)) {
					return;
				}
				callBack.onSuccess(result);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if (ex instanceof HttpException) { // 网络错误
					HttpException httpEx = (HttpException) ex;
					int responseCode = httpEx.getCode();
					String responseMsg = httpEx.getMessage();
					String errorResult = httpEx.getResult();
					// ...
					// LogUtil.e(responseCode + "\n" + responseMsg + "\n" +
					// errorResult);

					Toast.makeText(x.app(), responseCode + "\n" + responseMsg + "\n" + errorResult, Toast.LENGTH_LONG)
							.show();

				} else { // 其他错误
					// LogUtil.e(ex.getMessage());
					// Toast.makeText(x.app(), ex.getMessage(),
					// Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Toast.makeText(x.app(), "cancelled", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFinished() {
			}
		});
	}

	public static void get(final RequestParams requestParams, final RequestListener callBack) {
		x.http().get(requestParams, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if (!checkResult(result, requestParams, callBack)) {
					return;
				}
				callBack.onSuccess(result);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if (ex instanceof HttpException) { // 网络错误
					HttpException httpEx = (HttpException) ex;
					int responseCode = httpEx.getCode();
					String responseMsg = httpEx.getMessage();
					String errorResult = httpEx.getResult();
					// ...
					// LogUtil.e(responseCode + "\n" + responseMsg + "\n" +
					// errorResult);

					Toast.makeText(x.app(), responseCode + "\n" + responseMsg + "\n" + errorResult, Toast.LENGTH_LONG)
							.show();

				} else { // 其他错误
					// LogUtil.e(ex.getMessage());
					// Toast.makeText(x.app(), ex.getMessage(),
					// Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Toast.makeText(x.app(), "cancelled", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFinished() {
			}
		});
	}

	public static void get(final Activity activity, final RequestParams requestParams, final RequestListener callBack) {
		final Dialog dialog = CustomProgressDialog.showWaitDialog(activity);
		x.http().get(requestParams, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if (!checkResult(result, requestParams, callBack)) {
					return;
				}
				callBack.onSuccess(result);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if (ex instanceof HttpException) { // 网络错误
					HttpException httpEx = (HttpException) ex;
					int responseCode = httpEx.getCode();
					Toast.makeText(x.app(), "网络错误:" + responseCode, Toast.LENGTH_LONG).show();
					// String responseMsg = httpEx.getMessage();
					// // ...
					// Toast.makeText(x.app(), responseCode + "--\\n" +
					// responseMsg, Toast.LENGTH_LONG).show();

				} else { // 其他错误
					Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Toast.makeText(x.app(), "cancelled", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFinished() {
				dialog.dismiss();
			}
		});
	}

	public static void get(String url, final RequestListener callBack) {
		RequestParams requestParams = new RequestParams(url);
		get(requestParams, callBack);
	}

	public static boolean checkResult(String result, final RequestParams requestParams,
			final RequestListener callBack) {
		return checkResult(result, requestParams, callBack, true);
	}

	public static boolean checkResult(String result, final RequestParams requestParams, final RequestListener callBack,
			boolean flag) {

		JSONObject jsonObject = null;
		String code = "";
		try {
			jsonObject = new JSONObject(result);
		} catch (Exception e) {
		}
		try {
			code = jsonObject.getString("code");

		} catch (Exception e) {
		}
		String succ = "";
		try {
			succ = jsonObject.getString("succ");

		} catch (Exception e) {
		}
		String msg = "";
		try {
			msg = jsonObject.getString("msg");

		} catch (Exception e) {

		}

		if (code.equals("999") && flag) {

			LogUtil.e(result);
			String mobile = PreferencesUtils.getString(x.app(), LoginActivity.MOBILE);
			String password = PreferencesUtils.getString(x.app(), LoginActivity.PASSWORD);
			if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password)) {
				ActivityUtil.startActivity(LoginActivity.class);
				return false;
			}
			final DemoApplication userApplication = DemoApplication.getInstance();
			userApplication.setLogin(false);
			RequestParams logrequestParams = new RequestParams(HttpAPI.LOGIN);
			logrequestParams.addBodyParameter("account", mobile);
			logrequestParams.addBodyParameter("pwd", password);
			logrequestParams.addBodyParameter("type", HttpAPI.CLIENT_TYPE + "");

			HttpLib.get(logrequestParams, new RequestListener() {

				@Override
				public void onSuccess(String result) {
					MyUser myUser = JSON.parseObject(result, MyUser.class);
					userApplication.setUser(myUser);
					userApplication.setLogin(true);
					HttpLib.get(requestParams, callBack);
				}

				@Override
				public void onError(String error) {
					callBack.onError(error);
					ActivityUtil.startActivity(LoginActivity.class);
				}
			});

			return false;

		} else if (succ.equals("false")) {
			LogUtil.e(result);
			if (flag) {
				Toast.makeText(x.app(), msg, Toast.LENGTH_SHORT).show();
			}
			callBack.onError(result);
			return false;
		} else {
			LogUtil.d(result);
			return true;
		}

	}

	public static void post(final RequestParams requestParams, final RequestListener callBack) {

		x.http().post(requestParams, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if (!checkResult(result, requestParams, callBack)) {
					return;
				}
				callBack.onSuccess(result);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if (ex instanceof HttpException) { // 网络错误
					HttpException httpEx = (HttpException) ex;
					int responseCode = httpEx.getCode();
					String responseMsg = httpEx.getMessage();
					String errorResult = httpEx.getResult();
					// ...
					LogUtil.e(responseCode + "\n" + responseMsg + "\n" + errorResult);
					Toast.makeText(x.app(), responseCode + "\n" + responseMsg + "\n" + errorResult, Toast.LENGTH_LONG)
							.show();

				} else { // 其他错误
					LogUtil.e(ex.getMessage());
					Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFinished() {
			}
		});
	}

	public static void post(final Activity activity, final RequestParams requestParams,
			final RequestListener callBack) {
		final Dialog dialog = CustomProgressDialog.showWaitDialog(activity);
		x.http().post(requestParams, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if (!checkResult(result, requestParams, callBack)) {
					return;
				}
				callBack.onSuccess(result);
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if (ex instanceof HttpException) { // 网络错误
					HttpException httpEx = (HttpException) ex;
					int responseCode = httpEx.getCode();
					Toast.makeText(x.app(), "网络错误:" + responseCode, Toast.LENGTH_LONG).show();
					// String responseMsg = httpEx.getMessage();
					// // ...
					// Toast.makeText(x.app(), responseCode + "--\\n" +
					// responseMsg, Toast.LENGTH_LONG).show();

				} else { // 其他错误
					Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFinished() {
				dialog.dismiss();
			}
		});
	}

	public static void post(String url, RequestListener callBack) {
		RequestParams requestParams = new RequestParams(url);
		post(requestParams, callBack);
	}

	public static void upload(Activity context, File file, final UploadListener uploadListener) {
		RequestParams requestParams = new RequestParams(HttpAPI.UPLOADFILE);
		requestParams.setMultipart(true);
		requestParams.addBodyParameter("file", file);
		requestParams.addBodyParameter("rootPath", "jiabao");
		post(context, requestParams, new RequestListener() {
			@Override
			public void onSuccess(String result) {
				String url = "";
				try {
					JSONObject jsonObject = new JSONObject(result);
					url = jsonObject.getString("img");

				} catch (JSONException e) {
					e.printStackTrace();
				}
				uploadListener.onSuccess(url);
			}

			@Override
			public void onError(String error) {
				uploadListener.onError(error);
			}
		});
	}

	public static void upload(Activity context, final String picPath, final ImageView imageView) {
		HttpLib.upload(context, new File(BitmapHelper.compressBitmap(context, picPath, 300, 300, false)),
				new UploadListener() {
					@Override
					public void onSuccess(String url) {
						imageView.setTag(url);
						ImageLoader.getInstance().displayImage("file://" + picPath, imageView);
					}

					@Override
					public void onError(String error) {

					}
				});
	}

	public static void uploadMulti(Activity context, List<File> files, final UploadMultiListener uploadMultiListener) {
		RequestParams requestParams = new RequestParams(HttpAPI.UPLOADFILES);
		requestParams.setMultipart(true);
		if (files != null) {
			for (File file : files) {
				requestParams.addBodyParameter("files",
						new File(BitmapHelper.compressBitmap(context, file.getPath(), 300, 300, false)));
			}
		}
		requestParams.addBodyParameter("rootPath", "jiabao");
		post(context, requestParams, new RequestListener() {
			@Override
			public void onSuccess(String result) {

				// String[] strlist = null;
				// try {
				// JSONObject jsonObject = new JSONObject(result);
				// String url = jsonObject.getString("url");
				// strlist = url.split(",");
				// } catch (JSONException e) {
				// e.printStackTrace();
				// }
				String url = "";
				try {
					JSONObject jsonObject = new JSONObject(result);
					url = jsonObject.getString("imgs");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				uploadMultiListener.onSuccess(url);
			}

			@Override
			public void onError(String error) {
				uploadMultiListener.onError(error);
			}
		});
	}

}
