package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Product> productList;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        productList = new ArrayList<>();
        productList.add(new Product("T-Shirt", "Comfy cotton T-shirt", 59.90, R.drawable.tshirt));
        productList.add(new Product("Sneakers", "Sport sneakers with grip", 199.90, R.drawable.sneakers));
        productList.add(new Product("Jacket", "Winter puffer jacket", 349.90, R.drawable.jacket));

        adapter = new ProductAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        EasyCSChatSDK.init(this);
        recyclerView.post(EasyCSChatSDK::showBubble);
    }
}
