package com.example.netframe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;


/**
 * Created by 曾志强 on 2016/6/17.
 */
public abstract class BaseRecycleviewAdapter<T> extends RecyclerView.Adapter<MyRecycleViewHolder>{

    private List<T> data;
    private Context context;
    protected BaseRecycleviewAdapter(Context context,List<T> list){
        System.out.println("显示父构造器");

        this.context=context;
        this.data=list;
    }
    @Override
    public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(ItemViewId(),parent,false);
        return new MyRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyRecycleViewHolder holder, int position) {
        System.out.println("显示构造器绑定");
        bindViewHoder(holder.getViewHolder(), position);
        if(onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("显示构造器绑定"+"eeee");
                    onItemClickListener.onItemClick(null,v,holder.getPosition(),holder.getItemId());
                }
            });
        }
    }


    /**绑定动作
     */
    public abstract void bindViewHoder(MyViewHolder holder,int position);

    /**itemview的id
     * @return
     */
    public abstract int ItemViewId();
    @Override
    public int getItemCount() {
        return data.size();
    }

    private AdapterView.OnItemClickListener onItemClickListener;


    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
