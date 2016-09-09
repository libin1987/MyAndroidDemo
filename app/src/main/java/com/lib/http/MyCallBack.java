package com.lib.http;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;

import android.widget.Toast;

public class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType> {

	@Override
	public void onSuccess(ResultType result) {
		
		
	}

	@Override
	public void onError(Throwable ex, boolean isOnCallback) {
		if (ex instanceof HttpException) { // 网络错误
			HttpException httpEx = (HttpException) ex;
			int responseCode = httpEx.getCode();
			String responseMsg = httpEx.getMessage();
			// ...
			Toast.makeText(x.app(), responseCode + "--\\n" + responseMsg, Toast.LENGTH_LONG).show();

		} else { // 其他错误
			Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onCancelled(CancelledException cex) {

	}

	@Override
	public void onFinished() {

	}
}