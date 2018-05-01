package com.postpc.imri.ex1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageHolder> {

    final List<ChatMessage> data;
    private final OnClickListener listener;

    public MessageListAdapter(List<ChatMessage> input, OnClickListener listener) {
        this.data = input;
        this.listener = listener;

    }

    public void addMessage(ChatMessage msg) {
        data.add(msg);
        notifyItemInserted(data.size() - 1);
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_list, parent, false);
        MessageHolder holder = new MessageHolder(view);
        holder.setOnClickListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        ChatMessage msg = data.get(position);
        holder.timeText.setText(DateUtils.getRelativeTimeSpanString(holder.itemView.getContext(), msg.getTimeStamp()));
        holder.messageText.setText(msg.getMessage());
        holder.nameText.setText(msg.getSenderName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItem(ChatMessage msg) {
        for(int i = 0, size = data.size(); i < size; i++) {
            if (msg.toString().equals(data.get(i).toString())) {
                data.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
        throw new IllegalArgumentException("item is not in dataset");
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView messageText;
        TextView timeText;
        OnClickListener listener;

        MessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onClick(data.get(getAdapterPosition()));
                        return true;
                    }
                    return false;
                }
            });
        }

        void setOnClickListener(OnClickListener onClickListener) {
            this.listener = onClickListener;
        }
    }

    interface OnClickListener {
        void onClick(ChatMessage message);
    }

}
