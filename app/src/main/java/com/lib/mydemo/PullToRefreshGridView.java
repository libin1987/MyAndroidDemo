package com.lib.mydemo;

import android.os.Bundle;

import com.lib.base.BaseActivity;
import com.lib.base.RefreshGridHelper;
import com.lib.mydemo.adapter.ListItemAdapter;
import com.lib.mydemo.model.ListItemModel;

import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;
import java.util.List;

public class PullToRefreshGridView extends BaseActivity {

    @ViewInject(R.id.pullToRefreshGridView)
    com.handmark.pulltorefresh.library.PullToRefreshGridView gv;

    RefreshGridHelper<ListItemModel> refreshGridHelper=new RefreshGridHelper<>(this);

    List<ListItemModel> datas = Arrays.asList(
            new ListItemModel("name1", "content"),
            new ListItemModel("name2", "content"),
            new ListItemModel("name3", "content")
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_grid_view);
        gv.getRefreshableView();
        refreshGridHelper.pullToRefreshGridView = gv;
        refreshGridHelper.adapter = new ListItemAdapter();
        refreshGridHelper.tempData = datas;
        refreshGridHelper.api = "";
        refreshGridHelper.initRefresh();
    }
}
