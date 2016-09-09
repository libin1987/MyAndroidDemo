package com.lib.base;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import com.lib.mydemo.R;

import android.R.color;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import info.hoang8f.android.segmented.SegmentedGroup;

public class BaseActivity extends AppCompatActivity {
	private BroadcastReceiver broadcastReceiver;
	private Toast mToast;
	@ViewInject(R.id.toolbar_title)
	protected TextView mToolbarTitleTextView;
	@ViewInject(R.id.toolbar_search)
	protected LinearLayout mToolbarSearch;

	@ViewInject(R.id.toolbar)
	protected Toolbar mToolbar;

	@ViewInject(R.id.toolbar_seg_tab)
	protected SegmentedGroup toolbar_seg_tab;

	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
		x.view().inject(this);

		// 竖屏，不翻转
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// 软键盘设置
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		// 初始化工具栏
		initializeToolbar();
	}

	public void showToast(String message) {
		if (mToast != null) {
			mToast.cancel();
			mToast = null;
		}
		mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		mToast.show();
	}

	protected void initializeToolbar() {
		if (mToolbar == null) {
			throw new IllegalStateException("Layout is required to include a Toolbar with id toolbar");
		}
		setSupportActionBar(mToolbar);
		mToolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);
		// 隐藏默认导航栏标题
		if (mToolbarTitleTextView != null) {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.top_left);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		// toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}

	protected EditText getSerachBar() {
		mToolbarTitleTextView.setVisibility(View.GONE);
		mToolbarSearch.setVisibility(View.VISIBLE);
		EditText editSearch = new EditText(this);
		editSearch.setFocusable(false);
		editSearch.setCompoundDrawablePadding(8);
		editSearch.setBackgroundResource(R.drawable.top_seach_bg);
		editSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.top_seach, 0, 0, 0);
		mToolbarSearch.addView(editSearch);
		return editSearch;
	}

	protected void addTabButton(String title, int id) {
		if (toolbar_seg_tab.getVisibility() != View.VISIBLE) {
			toolbar_seg_tab.setVisibility(View.VISIBLE);
		}
		RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.radio_button_item, null);
		if (id == 1) {
			radioButton.setChecked(true);
		}
		radioButton.setId(id);
		radioButton.setText(title);
		radioButton.setBackgroundColor(getResources().getColor(R.color.white));

		radioButton.setLayoutParams(new RadioGroup.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
		// toolbar_seg_tab.check(radioButton.getId());
		toolbar_seg_tab.addView(radioButton);
		toolbar_seg_tab.updateBackground();
	}

	protected void removeAllTabButton() {
		toolbar_seg_tab.removeAllViews();
	}

	protected void removeTabButton(SegmentedGroup group) {
		if (group.getChildCount() < 1)
			return;
		group.removeViewAt(group.getChildCount() - 1);
		group.updateBackground();

		// Update margin for last item
		if (group.getChildCount() < 1)
			return;
		RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 0, 0, 0);
		group.getChildAt(group.getChildCount() - 1).setLayoutParams(layoutParams);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (!isChild()) {
			onTitleChanged(getTitle(), getTitleColor());
		}
	}

	@Override
	protected void onTitleChanged(CharSequence title, int color) {
		super.onTitleChanged(title, color);
		if (mToolbarTitleTextView != null) {
			mToolbarTitleTextView.setText(title);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		if (title != "") {
			toolbar_seg_tab.setVisibility(View.GONE);
		}
		mToolbarTitleTextView.setText(title);
	}

	/**
	 * 注册一个广播，监听定位结果，接受广播获得地址信息
	 */
	protected void registerBaiDuBroadCast(BroadcastReceiver broadcastReceiver) {
		DemoApplication.getInstance().locationService.start();
		this.broadcastReceiver = broadcastReceiver;
		IntentFilter intentToReceiveFilter = new IntentFilter();
		intentToReceiveFilter.addAction(DemoApplication.LOCATION_BCR);
		registerReceiver(broadcastReceiver, intentToReceiveFilter);
	}

	@Override
	protected void onDestroy() {
		if (broadcastReceiver != null) {
			unregisterReceiver(broadcastReceiver);
		}
		super.onDestroy();
	}
}
