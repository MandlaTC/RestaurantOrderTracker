package com.example.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.DateFormatter;
import com.example.data_models.Order;
import com.example.testrequests.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class CustomerPastOrdersAdapter extends
        RecyclerView.Adapter<CustomerPastOrdersAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onThumbsUpItemClick(int position);

        void onThumbsDownItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.onRatingItemClickListener = mListener;
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView restaurantNameTextView;
        TextView createdAtTextView;
        TextView orderStatusTextView;
        AppCompatButton thumbsUpRating;
        AppCompatButton thumbsDownRating;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.customer_past_order_restaurant_text_view);
            createdAtTextView = itemView.findViewById(R.id.customer_past_orderCreatedAt);
            orderStatusTextView = itemView.findViewById(R.id.customer_past_order_layout_order_status);
            thumbsUpRating = itemView.findViewById(R.id.customer_past_order_thumbs_up_icon);
            thumbsDownRating = itemView.findViewById(R.id.customer_past_order_thumbs_down_icon);
            thumbsUpRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRatingItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onRatingItemClickListener.onThumbsUpItemClick(position);
                        }
                    }
                }
            });
            thumbsDownRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRatingItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onRatingItemClickListener.onThumbsDownItemClick(position);
                        }
                    }
                }
            });
        }
    }

    private List<Order> orders;
    private OnItemClickListener onRatingItemClickListener;

    public CustomerPastOrdersAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.customer_past_order_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.restaurantNameTextView.setText("Order from: " + order.getRestaurantName());
        holder.createdAtTextView.setText(DateFormatter.formattedDate(order.getOrderCreatedAt()));
        holder.orderStatusTextView.setText("Status: " + order.getOrderStatus());
        Drawable thumbsUpDrawable = holder.itemView.getContext().getDrawable(R.drawable.ic_thumbs_up_icon_24dp);
        Drawable thumbsDownDrawable = holder.itemView.getContext().getDrawable(R.drawable.ic_thumbs_down_icon_24dp);

        if (order.getRating() == 1) {
            thumbsUpDrawable.setTint(Color.GREEN);
            thumbsDownDrawable.setTint(Color.GRAY);
        } else if (order.getRating()  == 0 ){
            thumbsDownDrawable.setTint(Color.RED);
            thumbsUpDrawable.setTint(Color.GRAY);
        }
        holder.thumbsUpRating.setBackgroundDrawable(thumbsUpDrawable);
        holder.thumbsDownRating.setBackgroundDrawable(thumbsDownDrawable);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
