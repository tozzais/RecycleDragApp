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


public class MenuAdapter extends RecyclerView.Adapter {

    private List<GridItemBean> data = new ArrayList<>();

    private LayoutInflater inflater;
    private Context context;

    public MenuAdapter(Context context, @NonNull List<GridItemBean> data) {
        this.context = context;
        if (data != null) {
            this.data = data;
        }
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (0 == viewType) {
            holder = new TitleViewHolder(inflater.inflate(R.layout.layout_title_first, parent, false));
        } else if (2 == viewType){
            holder = new SecondTitleViewHolder(inflater.inflate(R.layout.layout_title_second, parent, false));
        } else {
            holder = new FunctionViewHolder(inflater.inflate(R.layout.layout_grid_item, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        GridItemBean functionItem = data.get(position);
        if (0 == getItemViewType(position)) {
            TitleViewHolder holder = (TitleViewHolder) viewHolder;
            holder.text.setText(functionItem.name);
        }else if (2 == getItemViewType(position)) {
            SecondTitleViewHolder holder = (SecondTitleViewHolder) viewHolder;
            holder.text.setText(functionItem.name);
        } else {
            final int index = position;
            FunctionViewHolder holder = (FunctionViewHolder) viewHolder;
            setImage(functionItem.imageUrl,holder.iv);
            holder.text.setText(functionItem.name);
            holder.btn.setImageResource(functionItem.isSelect ? R.drawable.ic_block_selected : R.drawable.ic_block_add);
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GridItemBean f = data.get(index);
                    if (!f.isSelect) {
                        if (listener != null) {
                            if (listener.add(f)) {
                                f.isSelect = true;
                                notifyDataSetChanged();
                            }
                        }
                    }
                }
            });
        }
    }

    public void setImage(String url, ImageView iv) {
        try {
            int rid = context.getResources().getIdentifier(url,"drawable",context.getPackageName());
            iv.setImageResource(rid);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        private TextView text;

        public TitleViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    private class SecondTitleViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public SecondTitleViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    private class FunctionViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv, btn;
        private TextView text;

        public FunctionViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            text = (TextView) itemView.findViewById(R.id.text);
            btn = (ImageView) itemView.findViewById(R.id.btn);
        }
    }

    public interface OnItemAddListener {
        boolean add(GridItemBean item);
    }

    private OnItemAddListener listener;

    public void setOnItemAddListener(OnItemAddListener listener) {
        this.listener = listener;
    }

}
