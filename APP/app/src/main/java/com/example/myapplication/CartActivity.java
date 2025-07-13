package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRecycler;
    TextView totalView;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecycler = findViewById(R.id.cartRecycler);
        totalView = findViewById(R.id.totalText);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back); // ✅ your icon
        }



        List<CartItem> cartItems = ProductDetailsActivity.cart;

        cartAdapter = new CartAdapter(this, cartItems);
        cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        cartRecycler.setAdapter(cartAdapter);

        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice();
        }
        totalView.setText("Total: ₪" + total);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // return to previous screen
        return true;
    }


}
