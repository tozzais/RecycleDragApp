package com.example.recycledragapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter {

    private List<GridItemBean> data = new ArrayList<>();

    private LayoutInflater inflater;
    private Context context;

    public MyAdapter(Context context, @NonNull List<GridItemBean> data) {
        this.context = context;
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
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        GridItemBean functionItem = data.get(position);
        TitleViewHolder holder = (TitleViewHolder) viewHolder;
        holder.text.setData(functionItem);
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

        private MenuRelativeView text;

        public TitleViewHolder(View itemView) {
            super(itemView);
            text = (MenuRelativeView) itemView.findViewById(R.id.menu_view);
        }
    }
}
