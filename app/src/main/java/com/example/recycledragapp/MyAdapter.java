package com.example.recycledragapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter {

    private List<GridItemBean> data = new ArrayList<>();

    private LayoutInflater inflater;

    public MyAdapter(Context context, @NonNull List<GridItemBean> data) {
        if (data != null) {
            this.data = data;
        }
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        holder = new TitleViewHolder(inflater.inflate(R.layout.item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        GridItemBean functionItem = data.get(position);
        TitleViewHolder holder = (TitleViewHolder) viewHolder;
        holder.menuRelativeView.setData(functionItem);
        holder.menuRelativeView.setOnChangePositionListener(new MenuRelativeView.OnChangePositionListener() {
            @Override
            public void onUp() {
                int location = viewHolder.getLayoutPosition();
                Log.e("position",location+"");
                if (location>0){
                   change(location,location-1);
                }
            }

            @Override
            public void onDown() {
                //移除后使用新的position
                int location = viewHolder.getLayoutPosition();
                Log.e("position",location+"");
                if (location<getItemCount()-1){
                    change(location,location+1);
                }
            }
        });
    }
    private void change(int fromPosition,int targetPosition){
        Collections.swap(data, fromPosition, targetPosition);
        notifyItemMoved(fromPosition, targetPosition);
    }



    @Override
    public int getItemViewType(int position) {
        return data.get(position).status;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class TitleViewHolder extends RecyclerView.ViewHolder {

        private MenuRelativeView menuRelativeView;

        public TitleViewHolder(View itemView) {
            super(itemView);
            menuRelativeView =  itemView.findViewById(R.id.menu_view);
        }
    }
}
