package com.example.netframe.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 曾志强 on 2016/6/17.
 */
public class MyRecycleViewHolder extends RecyclerView.ViewHolder{

    private MyViewHolder viewHolder;

    public MyRecycleViewHolder(View itemView) {
        super(itemView);
        viewHolder=MyViewHolder.getViewHolder(itemView);
    }


    public MyViewHolder getViewHolder() {
        return viewHolder;
    }
}
