package com.xiajun.base;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.xiajun.utils.LogManager;

import java.util.ArrayList;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected String TAG = getClass().getSimpleName();
    public Context context;
    public final LayoutInflater inflater;
    public ArrayList<T> list;
    public OnItemClickListener<T> onItemClickListener;
    public OnItemLongClickListener<T> onItemLongClickListener;

    public BaseRecyclerViewAdapter(Context context, ArrayList<T> list) {
        this.context = context;
        this.inflater = ((Activity) context).getLayoutInflater();
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public LayoutInflater getLayoutInflater() {
        return inflater;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setOnItemClickListener(View holder, int position) {
        if (onItemClickListener != null) {
            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(position, getList().get(position));
                }
            });
        }
    }

    public void setOnItemLongClickListener(View holder, int position) {
        if (onItemLongClickListener != null) {
            holder.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemLongClickListener.onItemLongClick(view, position, getList().get(position));
                    return true;
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T> mListener) {
            this.onItemClickListener = mListener;
    }

    public OnItemClickListener<T> getOnItemClickListener() {
        return this.onItemClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> mListener) {
        if (mListener != null)
            this.onItemLongClickListener = mListener;
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View itemView, int position, T data);
    }

    protected void logE(String tag, String msg) {
        LogManager.logE(tag, msg);
    }

    protected int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

}