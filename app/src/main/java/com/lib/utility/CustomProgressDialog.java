package com.lib.utility;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * @Description:自定义对话框
 * @author http://blog.csdn.net/finddreams
 */
public class CustomProgressDialog extends ProgressDialog {

	private AnimationDrawable mAnimation;
	private Context mContext;
	private ImageView mImageView;
	private String mLoadingTip;
	private TextView mLoadingTv;
	private int mResid;
	private int i;
	private RelativeLayout dialog_view;
	

	public CustomProgressDialog(Context context, String content, int id,int theme,int i) {
		super(context,theme);
		this.mContext = context;
		this.mLoadingTip = content;
		this.mResid = id;
		this.i=i;
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		if (this.i==0) {
			initData();
		}
		else {
			initData2();
		}
	}

	private void initView() {
//		setContentView(R.layout.my_progress_dialog);
//		mLoadingTv = (TextView) findViewById(R.id.loadingTv);
//		mImageView = (ImageView) findViewById(R.id.loadingIv);
//		dialog_view = (RelativeLayout) findViewById(R.id.dialog_view);
	}
	private void initData() {

		mImageView.setBackgroundResource(mResid);
		// 通过ImageView对象拿到背景显示的AnimationDrawable
		mAnimation = (AnimationDrawable) mImageView.getBackground();
		// 为了防止在onCreate方法中只显示第一帧的解决方案之一
		mImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();

			}
		});
		mLoadingTv.setText(mLoadingTip);

	}

	private void initData2() {
//		dialog_view.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
//		dialog_view.setBackgroundResource(R.drawable.runingman_bg_loading);
//		mImageView.setBackgroundResource(mResid);
//		
//		 Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
//		mContext, R.anim.runingman_loading_animation);
//	    // 使用ImageView显示动画
//		mImageView.startAnimation(hyperspaceJumpAnimation);
//		
//		mLoadingTv.setText(mLoadingTip);
	}
	
	public void setContent(String str) {
		mLoadingTv.setText(str);
	}


	/*@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		mAnimation.start(); 
		super.onWindowFocusChanged(hasFocus);
	}*/
	
	
//	/**
//	 * 显示美团进度对话框
//	 * @param v
//	 */
//	public static Dialog showmeidialog(Activity context){
//		CustomProgressDialog dialog =new CustomProgressDialog(context, "正在加载中",R.anim.runingman_frame,R.style.TRANSDIALOG,0);
//		return dialog;
//	}
//	
//	public static Dialog showdd(Activity context){
//		CustomProgressDialog dialog =new CustomProgressDialog(context, "正在加载中",R.drawable.progress_loading,R.style.loading_dialog,1);
//		return dialog;
//	}
	public static Dialog showsys(Activity context) {
		 ProgressDialog dialog = new ProgressDialog(context);
         dialog.setMessage("加载中");
         return dialog;
	}
	public static Dialog showsys(Activity context,String content) {
		 ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(content);
        return dialog;
	}
//
//	public static Dialog showMaterial(Activity context) {
//		MaterialDialog materialDialog= new MaterialDialog.Builder(context)
//         .title("")
//         .content("加载中")
//         .progress(true, 0)
//         .progressIndeterminateStyle(false)
//         .build();
//         return materialDialog;
//	}

	public static Dialog showWaitDialog(Activity context) {
		Dialog dialog = showsys(context);
		dialog.setCancelable(false);
		dialog.show();
		return dialog;
	}
}
