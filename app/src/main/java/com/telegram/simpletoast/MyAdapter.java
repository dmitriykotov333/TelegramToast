package com.telegram.simpletoast;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<User> mData = new ArrayList<>();
    private ItemClickListener<User> onItemClick;


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.message)
        TextView message;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.linear_layout)
        RelativeLayout linearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setData(List<User> mData) {
        this.mData = mData;
    }

    public void setOnItemClick(ItemClickListener<User> onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void reestablish(User user, int position) {
        mData.add(position, user);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, mData.size());
    }

    public User delete(int position) {
        User user = mData.get(position);
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
        return user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_telegram, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final User users = mData.get(position);
        holder.name.setText(users.getName());
        holder.message.setText(users.getMessage());
        holder.time.setText(users.getTime());
        Glide.with(holder.imageView)
                .load(users.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageView);
        holder.linearLayout.setOnClickListener(view1 -> onItemClick.onItemClickListener(position, users));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
