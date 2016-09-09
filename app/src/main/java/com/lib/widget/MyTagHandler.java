package com.lib.widget;

import java.util.ArrayList;

import org.xml.sax.XMLReader;

import com.lib.utility.BitmapHelper;

import android.content.Context;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;

public class MyTagHandler implements TagHandler {
	private Context context;
	ArrayList<String> urlsList=new ArrayList<>();
	int curIndex=0;
	public MyTagHandler(Context context) {
		this.context = context;
	}

	@Override
	public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
		// TODO Auto-generated method stub

		// 处理标签<img>
		if (tag.toLowerCase().equals("img")) {
			// 获取长度
			int len = output.length();
			// 获取图片地址
			ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
			String imgURL = images[0].getSource();
			
			// 使图片可点击并监听点击事件
			output.setSpan(new ImageClick(imgURL,curIndex), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			curIndex++;
		}
	}

	private class ImageClick extends ClickableSpan {
		private int curIndex;

		public ImageClick(String url, int curIndex) {
			this.curIndex=curIndex;
			urlsList.add(url);
		}

		@Override
		public void onClick(View widget) {
			BitmapHelper.imageBrower(context, curIndex, urlsList.toArray(new String[urlsList.size()]));
		}

	}

}
