package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private Button sendButton;

    private ChatMessageAdapter adapter;
    private List<ChatMessage> messageList;

    private String userId;
    private final String backendUrl = "http://10.0.2.2:5000";

    private final Handler handler = new Handler();
    private final int REFRESH_INTERVAL_MS = 3000;

    private final Runnable messagePoller = new Runnable() {
        @Override
        public void run() {
            fetchMessages();
            handler.postDelayed(this, REFRESH_INTERVAL_MS);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        }

        userId = getIntent().getStringExtra("userId");

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        messageList = new ArrayList<>();
        adapter = new ChatMessageAdapter(messageList);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(adapter);

        fetchMessages(); // initial load
        handler.postDelayed(messagePoller, REFRESH_INTERVAL_MS); // auto refresh

        sendButton.setOnClickListener(v -> {
            String msg = messageInput.getText().toString().trim();
            if (!msg.isEmpty()) {
                sendMessage(msg);
                messageInput.setText("");
            }
        });
    }

    private void fetchMessages() {
        new Thread(() -> {
            try {
                String query = String.format("?company=%s&app=%s",
                        URLEncoder.encode("EasyShop", "UTF-8"),
                        URLEncoder.encode("EasyShop", "UTF-8")
                );
                URL url = new URL(backendUrl + "/messages/" + userId + query);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }

                runOnUiThread(() -> {
                    try {
                        JSONArray arr = new JSONArray(response.toString());
                        messageList.clear();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject msg = arr.getJSONObject(i);
                            String sender = msg.optString("sender", "unknown");
                            String text = msg.optString("text", "");
                            messageList.add(new ChatMessage(sender, text));
                        }
                        adapter.notifyDataSetChanged();
                        chatRecyclerView.scrollToPosition(messageList.size() - 1);
                    } catch (Exception e) {
                        Log.e("ChatActivity", "Failed to parse messages", e);
                    }
                });

            } catch (Exception e) {
                Log.e("ChatActivity", "Failed to fetch messages", e);
            }
        }).start();
    }

    private void sendMessage(String message) {
        new Thread(() -> {
            try {
                URL url = new URL(backendUrl + "/messages");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JSONObject body = new JSONObject();
                body.put("userId", userId);
                body.put("text", message);
                body.put("sender", "client");
                body.put("company", "EasyShop");
                body.put("app", "EasyShop");


                Log.d("SEND", "Sending JSON: " + body.toString());

                OutputStream os = conn.getOutputStream();
                os.write(body.toString().getBytes());
                os.flush();

                int responseCode = conn.getResponseCode();
                Log.d("SEND", "Response Code: " + responseCode);

                if (responseCode == 200) {
                    runOnUiThread(this::fetchMessages);
                }

            } catch (Exception e) {
                Log.e("ChatActivity", "Failed to send message", e);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(messagePoller);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
