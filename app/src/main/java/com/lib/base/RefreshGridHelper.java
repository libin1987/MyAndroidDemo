package com.lib.base;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lib.http.HttpLib;
import com.lib.http.HttpLib.RequestListener;
import com.lib.mydemo.model.PagerJson;
import com.simple.commonadapter.ListViewAdapter;

import org.xutils.http.RequestParams;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author libin
 * @param <D>
 * @Description 使用方法 继承此类 this.adapter= CustomAdapter(); this.api =
 *              /this.tempData=items(假数据); //二选一
 *              this.pullToRefreshListView=listview;
 *              initPullToRefreshListView();
 */
public class RefreshGridHelper<D>  {

	private int pager = 1;
	private boolean notNext;
	private final Activity content;
	private static final String LIMIT = "limit";
	private static final String PAGER = "pager";

	public String api = "";
	public PullToRefreshGridView pullToRefreshGridView;
	public HashMap<String, String> extraParameters = new HashMap<>();
	public List<D> tempData;
	public ListViewAdapter<D> adapter;
	private Class<D> clazz;

	public RefreshGridHelper(Activity content) {
		this.content=content;
	}
	protected void initPullToRefreshListView() {
		clazz = (Class<D>) ((ParameterizedType) adapter.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

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
