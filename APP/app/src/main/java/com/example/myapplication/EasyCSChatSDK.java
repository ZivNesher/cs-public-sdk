package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

public class EasyCSChatSDK {

    private static Context context;
    private static String userId;

    private static FrameLayout bubbleLayout;
    private static WindowManager windowManager;


    public static void init(Context ctx){
        if (!(ctx instanceof Activity)) {
            Log.e("EasyCSChatSDK", "Context must be an Activity!");
            return;
        }
        context = ctx;
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void showBubble() {
        if (context == null) return;

        if (windowManager != null && bubbleLayout != null) return;

        Activity activity = (Activity) context;

        windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        bubbleLayout = new FrameLayout(activity);

        ImageView bubble = new ImageView(activity);
        bubble.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_chat_bubble));
        bubble.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        bubble.setOnTouchListener(new View.OnTouchListener() {
            private int lastAction;
            private int initialX, initialY;
            private float initialTouchX, initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (windowManager == null || bubbleLayout == null) return false;

                WindowManager.LayoutParams params = (WindowManager.LayoutParams) bubbleLayout.getLayoutParams();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        lastAction = event.getAction();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (initialTouchX - event.getRawX());
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(bubbleLayout, params);
                        lastAction = event.getAction();
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            // It's a click, not a drag
                            SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                            String userId = prefs.getString("userId", null);
                            if (userId == null) {
                                context.startActivity(new Intent(context, NamePromptActivity.class));
                            } else {
                                openChat(userId);
                            }
                        }
                        return true;
                }

                return false;
            }
        });


        bubbleLayout.addView(bubble);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                160,
                160,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.END;
        params.x = 50;
        params.y = 200;

        try {
            windowManager.addView(bubbleLayout, params);
        } catch (Exception e) {
            Log.e("EasyCSChatSDK", "Failed to add bubble view", e);
        }
    }


    public static void hideBubble() {
        if (windowManager != null && bubbleLayout != null) {
            windowManager.removeView(bubbleLayout);
            bubbleLayout = null;
            windowManager = null;
        }
    }

    private static void openChat(String userId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

}
