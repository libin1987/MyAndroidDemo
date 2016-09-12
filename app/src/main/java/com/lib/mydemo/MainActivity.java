package com.lib.mydemo;

import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lib.base.BaseActivity;
import com.lib.base.RefreshListHelper;
import com.lib.mydemo.adapter.ListItemAdapter;
import com.lib.mydemo.model.ListItemModel;
import com.lib.utility.CustomProgressDialog;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    @ViewInject(R.id.pullToRefreshListView)
    PullToRefreshListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        ArrayList<ListItemModel> datas = new ArrayList();
        datas.add(new ListItemModel("name1", "content"));
        datas.add(new ListItemModel("name2", "content"));
        datas.add(new ListItemModel("name3", "content"));
        RefreshListHelper<ListItemModel> refreshListHelper = new RefreshListHelper<>(this);
        
        refreshListHelper.pullToRefreshListView = lv;
        refreshListHelper.adapter = new ListItemAdapter(datas);
        refreshListHelper.tempData = datas;
        refreshListHelper.api = "";
        refreshListHelper.initPullToRefreshListView();
    }


    @Event(R.id.toolbar_title)
    private void onClick(View view) {
        CustomProgressDialog.showsys(this);
    }
}
