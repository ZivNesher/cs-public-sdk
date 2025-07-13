package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NamePromptActivity extends AppCompatActivity {

    EditText nameInput;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_prompt);

        nameInput = findViewById(R.id.nameInput);
        continueButton = findViewById(R.id.continueButton);

        continueButton.setOnClickListener(v -> {
            String enteredName = nameInput.getText().toString().trim();
            if (enteredName.isEmpty()) {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
                return;
            }

            checkIfNameExists(enteredName, exists -> {
                if (exists) {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Name already taken, choose another", Toast.LENGTH_SHORT).show()
                    );
                } else {
                    // Save and continue
                    SharedPreferences prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                    prefs.edit().putString("userId", enteredName).apply();

                    Intent i = new Intent(NamePromptActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        });
    }

    private void checkIfNameExists(String name, NameCheckCallback callback) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:5000/users?company=Mastercard&app=EasyShop");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder result = new StringBuilder();
                while (scanner.hasNext()) {
                    result.append(scanner.nextLine());
                }

                JSONArray users = new JSONArray(result.toString());
                boolean exists = false;
                for (int i = 0; i < users.length(); i++) {
                    if (users.getString(i).equalsIgnoreCase(name)) {
                        exists = true;
                        break;
                    }
                }
                callback.onCheckComplete(exists);
            } catch (Exception e) {
                e.printStackTrace();
                callback.onCheckComplete(false); // fail-safe
            }
        }).start();
    }

    interface NameCheckCallback {
        void onCheckComplete(boolean exists);
    }
}
