package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.DateFormatter;
import com.example.data_models.Order;
import com.example.testrequests.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class CustomerUpcomingOrdesAdapter extends
        RecyclerView.Adapter<CustomerUpcomingOrdesAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView restaurantNameTextView;
        TextView createdAtTextView;
        TextView orderStatusTextView;
        Button thumbsUpRating;
        Button thumbsDownRating;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.customer_past_order_restaurant_text_view);
            createdAtTextView = itemView.findViewById(R.id.customer_past_orderCreatedAt);
       //     orderStatusTextView = itemView.findViewById(R.id.customer_past_orders_success_text_view);
            thumbsUpRating = itemView.findViewById(R.id.order_layout_change_order_status);
            thumbsUpRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    private List<Order> orders;
    private OnItemClickListener mListener;

    public CustomerUpcomingOrdesAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.staff_order_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.restaurantNameTextView.setText("Order from: " + order.getRestaurantName());
        holder.createdAtTextView.setText(DateFormatter.formattedDate(order.getOrderCreatedAt()));
        holder.orderStatusTextView.setText("Status: " + order.getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
