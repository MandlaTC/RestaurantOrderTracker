package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data_models.Order;
import com.example.testrequests.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StaffOrderAdapter extends RecyclerView.Adapter<StaffOrderAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Order> orders;

    public StaffOrderAdapter(Context context, List<Order> orders) {
        inflater = LayoutInflater.from(context);
        this.orders =orders ;

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.staff_order_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //TODO: Change to customer name;

        holder.customerName.setText(orders.get(position).getCustomerID());
        holder.orderCreatedAt.setText(orders.get(position).getOrderCreatedAt().toString());
        holder.orderStatus.setText(orders.get(position).getOrderStatus());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, orderCreatedAt, orderStatus, changeOrderStatusButton;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.order_layout_customer_name);
            orderCreatedAt = itemView.findViewById(R.id.order_layout_createdAt);
            orderStatus = itemView.findViewById(R.id.order_layout_order_status);
            changeOrderStatusButton = itemView.findViewById(R.id.order_layout_change_order_status);

        }
    }
}
