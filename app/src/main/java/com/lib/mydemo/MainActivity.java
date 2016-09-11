package com.lib.mydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lib.base.RefreshListActivity;
import com.lib.mydemo.adapter.ListItemAdapter;
import com.lib.mydemo.model.ListItemModel;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends RefreshListActivity<ListItemModel> {

    @ViewInject(R.id.pullToRefreshListView)
    PullToRefreshListView lv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ListItemModel> datas =new ArrayList();
        datas.add(new ListItemModel("name1","content"));
        datas.add(new ListItemModel("name2","content"));
        datas.add(new ListItemModel("name3","content"));
        this.pullToRefreshListView=lv;
        this.adapter=new ListItemAdapter(datas);
        this.tempData=datas;
        this.api="";
        initPullToRefreshListView();
    }

}
