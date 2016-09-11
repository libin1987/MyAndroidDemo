package com.lib.base;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.xutils.http.RequestParams;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.lib.http.HttpLib;
import com.lib.http.HttpLib.RequestListener;
import com.lib.mydemo.model.PagerJson;
import com.simple.commonadapter.ListViewAdapter;

import android.os.AsyncTask;
import android.widget.ListView;

public class RefreshListActivity<D> extends BaseActivity {

	protected int pager = 1;
	protected boolean notNext;

	protected static final String LIMIT = "limit";
	protected static final String PAGER = "pager";
	protected  HashMap<String, String> extraParameters =new HashMap<>();
	protected  String api;
	protected PullToRefreshListView pullToRefreshListView;
	protected List<D> tempData ;
	protected ListViewAdapter<D> adapter;
	private Class<D> clazz;

	@SuppressWarnings("unchecked")
	protected void initPullToRefreshListView() {
		clazz = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

		pullToRefreshListView.setAdapter(adapter);
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				pager = 1;
				notNext = false;
				getDatas();
			}
		});
		pullToRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

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
			pullToRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	public void getDatas() {
		if (api.equals("")) {
			
			new GetDataTask().execute();
			return;
		}
		RequestParams requestParams = new RequestParams(api);
		requestParams.addQueryStringParameter(LIMIT, "15");
		requestParams.addQueryStringParameter(PAGER, pager + "_");
		
		if (extraParameters != null) {
			Set<String> get = extraParameters.keySet();
			for (String key : get) {
				requestParams.addQueryStringParameter(key, extraParameters.get(key));
			}
		}
		HttpLib.get(this,requestParams, new RequestListener() {
			
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
				adapter.addItems(datas);
				pullToRefreshListView.onRefreshComplete();
				
			}

			@Override
			public void onError(String error) {
				pullToRefreshListView.onRefreshComplete();
			}
		});
	}
}
