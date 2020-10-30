package com.telegram.simpletoast;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<User> mData;
    private Context context;
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
            view.setOnClickListener(view1 -> onItemClick.onItemClickListener(mData.get(getAdapterPosition()), getAdapterPosition()));
        }
    }

    public RecyclerViewAdapter(Context context, List<User> mData, ItemClickListener<User> onItemClick) {
        this.context = context;
        this.mData = mData;
        this.onItemClick = onItemClick;
    }

    public void reestablish(User user, int position) {
        mData.add(position, user);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, mData.size());
        Log.i("test", user.getName());
    }

    public User deleteItem(int position) {
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
        Glide.with(context)
                .load(users.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
