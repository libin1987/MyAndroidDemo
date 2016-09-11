package com.lib.mydemo.adapter;

import com.lib.mydemo.R;
import com.lib.mydemo.model.ListItemModel;
import com.simple.commonadapter.ListViewAdapter;
import com.simple.commonadapter.viewholders.GodViewHolder;

import java.util.List;

/**
 * Created by libin on 2016/9/11.
 */
public class ListItemAdapter extends ListViewAdapter<ListItemModel>{


    public ListItemAdapter( List<ListItemModel> datas) {
        super(R.layout.item_view, datas);
    }

    @Override
    protected void onBindData(GodViewHolder viewHolder, int position, ListItemModel item) {
        viewHolder.setText(R.id.i_name,item.getName());
        viewHolder.setText(R.id.i_content,item.getContent());
    }
}
