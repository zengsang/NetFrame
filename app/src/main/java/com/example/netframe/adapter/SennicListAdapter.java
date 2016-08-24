package com.example.netframe.adapter;

import android.content.Context;
import android.widget.TextView;


import com.example.netframe.R;
import com.example.netframe.bean.SenniceData;

import java.util.List;

/**
 * Created by 曾志强 on 2016/8/4.
 */
public class SennicListAdapter extends BaseRecycleviewAdapter{

    private Context context;
    private List<SenniceData> Datas;
    public SennicListAdapter(Context context, List list) {
        super(context, list);
        this.Datas=list;
        this.context=context;
    }


    @Override
    public void bindViewHoder(MyViewHolder holder, int position) {
        System.out.println("商品列表显示" + Datas.size());
        ((TextView)holder.get(R.id.name)).setText(Datas.get(position).getTitle());
    }

    @Override
    public int ItemViewId() {
        return R.layout.item;
    }


}
