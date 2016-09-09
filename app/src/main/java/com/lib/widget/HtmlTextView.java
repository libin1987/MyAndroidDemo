package com.lib.widget;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

public class HtmlTextView extends TextView {

	public HtmlTextView(Context context) {
		super(context);
	}

	public HtmlTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HtmlTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Parses String containing HTML to Android's Spannable format and displays
	 * it in this TextView.
	 *
	 * @param html
	 *            String containing HTML, for example: "<b>Hello world!</b>"
	 */
	public void setHtmlFromString(String html, boolean useLocalDrawables) {
		Html.ImageGetter imgGetter;
		html = html.replace("\r\n", "<br/>").replace("<img", "<br/><img");
		if (useLocalDrawables) {
			imgGetter = new LocalImageGetter(getContext());
		} else {
			imgGetter = new UrlImageGetter(this, getContext());
		}
		// this uses Android's Html class for basic parsing, and HtmlTagHandler
		setText(Html.fromHtml(html, imgGetter, new MyTagHandler(getContext())));
		// make links work
		setMovementMethod(LinkMovementMethod.getInstance());
//		setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));
//		setTextIsSelectable(true);
		// no flickering when clicking textview for Android < 4, but overriders
		// color...
		// text.setTextColor(getResources().getColor(android.R.color.secondary_text_dark_nodisable));
	}

	// make links and image work
//	Handler handler = new Handler() {
//
//		public void handleMessage(Message msg) {
//			int what = msg.what;
//			if (what == 200) {
//				MessageSpan ms = (MessageSpan) msg.obj;
//				Object[] spans = (Object[]) ms.getObj();
//				for (Object span : spans) {
//					if (span instanceof ImageSpan) {
//						String url = ((ImageSpan) span).getSource();
//						String[] urls = { url };
//						BitmapHelper.imageBrower(getContext(), 0, urls);
//						// 处理自己的逻辑
//					}
//				}
//			}
//		}
//	};

}
