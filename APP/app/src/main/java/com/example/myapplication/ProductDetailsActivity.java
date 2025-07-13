package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView nameView, priceView, descView;
    ImageView imageView;
    Button addToCartBtn;
    public static List<CartItem> cart = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        }



        nameView = findViewById(R.id.detailName);
        priceView = findViewById(R.id.detailPrice);
        descView = findViewById(R.id.detailDesc);
        imageView = findViewById(R.id.detailImage);
        addToCartBtn = findViewById(R.id.addToCartBtn);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String desc = i.getStringExtra("desc");
        double price = i.getDoubleExtra("price", 0.0);
        int imageResId = i.getIntExtra("image", R.drawable.ic_launcher_foreground);

        nameView.setText(name);
        descView.setText(desc);
        priceView.setText("₪" + price);
        imageView.setImageResource(imageResId);

        addToCartBtn.setOnClickListener(v -> {
            cart.add(new CartItem(name, price, imageResId));

            finish();
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // return to previous screen
        return true;
    }


}
