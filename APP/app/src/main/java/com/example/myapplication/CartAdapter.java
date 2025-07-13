package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> items) {
        this.context = context;
        this.cartItems = items;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.name.setText(item.getName());
        holder.price.setText("₪" + item.getPrice());
        holder.image.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cartItemName);
            price = itemView.findViewById(R.id.cartItemPrice);
            image = itemView.findViewById(R.id.cartItemImage);
        }
    }
}
