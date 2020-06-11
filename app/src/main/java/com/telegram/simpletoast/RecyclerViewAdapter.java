package com.telegram.simpletoast;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<User> mData;
    private Context context;
    private ItemClickListener<User> onItemClick;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView message;
        TextView time;
        ImageView imageView;
        ConstraintLayout linearLayout;

        ViewHolder(View view) {
            super(view);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.image);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onItemClickListener(mData.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
    public RecyclerViewAdapter() {
    }
    public RecyclerViewAdapter(Context context, List<User> mData, ItemClickListener<User> onItemClick) {
        this.context = context;
        this.mData = mData;
        this.onItemClick = onItemClick;
    }
    public void deleteItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_telegram, parent, false);

        return new ViewHolder(itemView);
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
