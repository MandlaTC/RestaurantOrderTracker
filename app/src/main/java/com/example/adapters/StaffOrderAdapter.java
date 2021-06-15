package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data_models.Order;
import com.example.testrequests.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class StaffOrderAdapter extends
        RecyclerView.Adapter<StaffOrderAdapter.ViewHolder> {


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView customerNameTextView;
        TextView createdAtTextView;
        TextView orderStatusTextView;
        Button changeOrderStatusButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.order_layout_customer_name);
            createdAtTextView = itemView.findViewById(R.id.order_layout_createdAt);
            orderStatusTextView = itemView.findViewById(R.id.order_layout_order_status);
            changeOrderStatusButton = itemView.findViewById(R.id.order_layout_change_order_status);
        }
    }

    private List<Order> orders;

    public StaffOrderAdapter(List<Order> orders) {
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
        System.out.println("adapter customer name: " + order.getCustomerName());
        holder.customerNameTextView.setText(order.getCustomerName());
        holder.createdAtTextView.setText(order.getOrderCreatedAt().toString());
        holder.orderStatusTextView.setText(order.getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
