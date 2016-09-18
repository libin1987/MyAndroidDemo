package com.lib.mydemo;

import android.os.Bundle;

import com.lib.base.BaseActivity;
import com.lib.base.RefreshListHelper;
import com.lib.mydemo.adapter.ListItemAdapter;
import com.lib.mydemo.model.ListItemModel;

import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;
import java.util.List;

public class PullToRefreshListView extends BaseActivity {
    @ViewInject(R.id.pullToRefreshListView)
    com.handmark.pulltorefresh.library.PullToRefreshListView lv;
    RefreshListHelper<ListItemModel> refreshListHelper=new RefreshListHelper<>(this);

    List<ListItemModel> datas = Arrays.asList(
            new ListItemModel("name1", "content"),
            new ListItemModel("name2", "content"),
            new ListItemModel("name3", "content")
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_list_view);

        refreshListHelper.pullToRefreshListView = lv;
        refreshListHelper.adapter = new ListItemAdapter();
        refreshListHelper.tempData = datas;
        refreshListHelper.api = "";
        refreshListHelper.initRefresh();
    }
}
