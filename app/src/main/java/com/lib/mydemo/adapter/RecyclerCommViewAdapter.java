package com.lib.mydemo.adapter;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerCommSwipeAdapter;
import com.lib.mydemo.R;
import com.simple.commonadapter.viewholders.RecyclerViewHolder;

public class RecyclerCommViewAdapter<string> extends RecyclerCommSwipeAdapter<string> {

    public RecyclerCommViewAdapter() {
        super(R.layout.recyclerview_item);
    }

    @Override
    protected void onSwipeBindData(final RecyclerViewHolder viewHolder, final int position, string item) {
        final SwipeLayout swipeLayout;
        TextView textViewPos;
        final TextView textViewData;
        Button buttonDelete;

        swipeLayout = (SwipeLayout) viewHolder.findViewById(getSwipeLayoutResourceId(position));
        textViewPos = (TextView) viewHolder.findViewById(R.id.position);
        textViewData = (TextView) viewHolder.findViewById(R.id.text_data);
        buttonDelete = (Button) viewHolder.findViewById(R.id.delete);

        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(getClass().getSimpleName(), "onItemSelected: " + textViewData.getText().toString());
                Toast.makeText(view.getContext(), "onItemSelected: " + textViewData.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(viewHolder.getContext(), "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(swipeLayout);
                remove(position);
                mItemManger.closeAllItems();
                Toast.makeText(view.getContext(), "Deleted " + textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
            }
        });
        textViewPos.setText((position + 1) + ".");
        textViewData.setText((CharSequence) item);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}
