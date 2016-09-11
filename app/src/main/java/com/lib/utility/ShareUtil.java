package com.lib.utility;

import org.xutils.x;

import com.lib.http.HttpAPI;
import com.lib.mydemo.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import android.app.Activity;
import android.widget.Toast;

public class ShareUtil {
	private static UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {

			Toast.makeText(x.app(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(x.app(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(x.app(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
		}
	};

	public static void openShare(Activity activity,String title,String content) {
		if (content.length() > 100) {
			content = content.substring(0, 100) + "...";
		}
		UMImage image = new UMImage(activity, R.mipmap.logo);
		String url = HttpAPI.APPDOWNLOAD;
		new ShareAction(activity)
				.setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
				.withTitle(title).withText(content).withMedia(image).withTargetUrl(url).setCallback(umShareListener)
				.open();
	}

}
