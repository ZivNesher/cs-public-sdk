package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_CLIENT = 0;
    private static final int VIEW_TYPE_CS = 1;

    private final List<ChatMessage> messageList;

    public ChatMessageAdapter(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).sender.equals("client") ? VIEW_TYPE_CLIENT : VIEW_TYPE_CS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = (viewType == VIEW_TYPE_CLIENT)
                ? R.layout.item_message_client
                : R.layout.item_message_cs;

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);
        ((ChatViewHolder) holder).messageText.setText(message.text);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }
}
