package com.lib.base;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.xutils.http.RequestParams;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lib.http.HttpLib;
import com.lib.http.HttpLib.RequestListener;
import com.lib.mydemo.model.PagerJson;
import com.simple.commonadapter.ListViewAdapter;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

/**
 * @author libin
 * @param <D>
 * @Description 使用方法 继承此类 this.adapter= CustomAdapter(); this.api =
 *              /this.tempData=items(假数据); //二选一
 *              this.pullToRefreshListView=listview;
 *              initPullToRefreshListView();
 */
public class RefreshGridFragment<D> extends BaseFragment {

	protected int pager = 1;
	protected boolean notNext;

	protected static final String LIMIT = "limit";
	protected static final String PAGER = "pager";

	protected String api = "";
	protected PullToRefreshGridView pullToRefreshGridView;
	protected HashMap<String, String> extraParameters = new HashMap<>();
	protected List<D> tempData;
	protected ListViewAdapter<D> adapter;
	private Class<D> clazz;

	@SuppressWarnings("unchecked")
	protected void initPullToRefreshListView() {
		clazz = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

		pullToRefreshGridView.setAdapter(adapter);
		pullToRefreshGridView.setOnRefreshListener(new OnRefreshListener<GridView>() {

			@Override
			public void onRefresh(PullToRefreshBase<GridView> refreshView) {
				pager = 1;
				notNext = false;
				getDatas();
				
			}
		});
		pullToRefreshGridView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				if (notNext) {
					return;
				}
				pager++;
				getDatas();
			}
		});
		getDatas();
	}

	private class GetDataTask extends AsyncTask<Void, Void, ArrayList<D>> {

		@Override
		protected ArrayList<D> doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			return (ArrayList<D>) tempData;
		}

		@Override
		protected void onPostExecute(ArrayList<D> result) {
			if (pager == 1) {
				adapter.clear();
			}
			if (tempData != null) {
				adapter.addItems(result);
			}
			pullToRefreshGridView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	public void getDatas() {
		if (api.equals("")) {

			new GetDataTask().execute();
			return;
		}
		RequestParams requestParams = new RequestParams(api);
		requestParams.addQueryStringParameter(LIMIT, "11");
		requestParams.addQueryStringParameter(PAGER, pager + "_");
		if (extraParameters != null) {
			Set<String> get = extraParameters.keySet();
			for (String key : get) {
				requestParams.addQueryStringParameter(key, extraParameters.get(key));
			}
		}
		HttpLib.get(requestParams, new RequestListener() {

			@Override
			public void onSuccess(String result) {
				PagerJson pagerJson = JSON.parseObject(result, PagerJson.class);
				if (pager == 1) {
					adapter.clear();
				}
				if (pager >= pagerJson.getTotalPages()) {
					notNext = true;
				}
				List<D> datas = JSON.parseArray(pagerJson.getArrays(), clazz);
				Log.d("======datas========", datas.toString());

				adapter.addItems(datas);
				pullToRefreshGridView.onRefreshComplete();
			}

			@Override
			public void onError(String error) {
				pullToRefreshGridView.onRefreshComplete();
			}
		});
	}
}
