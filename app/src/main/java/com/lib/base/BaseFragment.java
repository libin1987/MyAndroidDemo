package com.lib.base;

import org.xutils.x;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by wyouflf on 15/11/4.
 */
public class BaseFragment extends Fragment {
	private BroadcastReceiver broadcastReceiver;
	private boolean injected = false;
	private Toast mToast;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		injected = true;
		return x.view().inject(this, inflater, container);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (!injected) {
			x.view().inject(this, this.getView());
		}
	}

	public void showToast(String message) {
		if (mToast != null) {
			mToast.cancel();
			mToast = null;
		}
		mToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
		mToast.show();
	}

	/**
	 * 注册一个广播，监听定位结果，接受广播获得地址信息
	 */
	protected void registerBaiDuBroadCast(BroadcastReceiver broadcastReceiver) {
		DemoApplication.getInstance().locationService.start();
		this.broadcastReceiver=broadcastReceiver;
		IntentFilter intentToReceiveFilter = new IntentFilter();
		intentToReceiveFilter.addAction(DemoApplication.LOCATION_BCR);
		getActivity().registerReceiver(broadcastReceiver, intentToReceiveFilter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (broadcastReceiver != null) {
			getActivity().unregisterReceiver(broadcastReceiver);
		}
	}
}
