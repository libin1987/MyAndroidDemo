package com.lib.base;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lib.http.HttpLib;
import com.lib.mydemo.model.PagerJson;
import com.simple.commonadapter.ListViewAdapter;

import org.xutils.http.RequestParams;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/12.
 */
public class RefreshListHelper <D>{

    private int pager = 1;
    private boolean notNext;
    private final Activity content;

    private static final String LIMIT = "limit";
    private static final String PAGER = "pager";
    public HashMap<String, String> extraParameters =new HashMap<>();
    public  String api;
    public PullToRefreshListView pullToRefreshListView;
    public List<D> tempData ;
    public ListViewAdapter<D> adapter;
    private Class<D> clazz;

    public RefreshListHelper(Activity content) {
        this.content=content;
    }

    public void initRefresh() {
        Type type = adapter.getClass().getGenericSuperclass();
        clazz = (Class<D>) ((ParameterizedType) type).getActualTypeArguments()[0];

        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                pager = 1;
                notNext = false;
                getDatas();
            }
        });
        pullToRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                if (notNext) {
                    return;
                }
                pager++;
                getDatas();
            }
        });

       pullToRefreshListView.firstReFreshing(true);
    }

    private class GetDataTask extends AsyncTask<Void, Void, List<D>> {

        @Override
        protected List<D> doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            return (List<D>) tempData;
        }

        @Override
        protected void onPostExecute(List<D> result) {
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
        HttpLib.get(content,requestParams, new HttpLib.RequestListener() {

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
