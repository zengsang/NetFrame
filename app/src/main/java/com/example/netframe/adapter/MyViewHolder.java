package com.example.netframe.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 曾志强 on 2016/6/17.
 */
public class MyViewHolder {
    private SparseArray<View> viewHolder;
    private View view;

    public static MyViewHolder getViewHolder(View view){
        MyViewHolder myViewHolder = (MyViewHolder) view.getTag();
        if (myViewHolder == null) {
            myViewHolder = new MyViewHolder(view);
            view.setTag(myViewHolder);
        }
        return myViewHolder;
    }
    private MyViewHolder(View view) {
        this.view = view;
        viewHolder = new SparseArray<View>();
        view.setTag(viewHolder);
    }
    public <T extends View> T get(int id) {
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public View getConvertView() {
        return view;
    }

    public TextView getTextView(int id) {

        return get(id);
    }
    public Button getButton(int id) {

        return get(id);
    }

    public ImageView getImageView(int id) {
        return get(id);
    }

    public void setTextView(int  id,CharSequence charSequence){
        getTextView(id).setText(charSequence);
    }

}