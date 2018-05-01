package com.postpc.imri.ex1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageListActivity extends AppCompatActivity implements MessageListAdapter.OnClickListener, MessageDetailsFragment.MessageDeletedListener {
    private RecyclerView mRecyclerView;
    private MessageListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button btn;
    private ArrayList<ChatMessage> messages;
    EditText mInput;
    private static final String MESSAGES = "messages";
    private static final String TAG = "MainActivity";


    @Override
    public void onSaveInstanceState(Bundle outState) {
        ArrayList<String> messages = new ArrayList<>(mAdapter.data.size());
        for (ChatMessage msg : mAdapter.data) {
            messages.add(msg.toString());
        }
        outState.putStringArrayList(MESSAGES, messages);

        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting the main activity xml reference
        setContentView(R.layout.activity_message_list);
        // attaching the recycler view to the correct layout
        mRecyclerView = findViewById(R.id.reyclerview_message_list);
        // retrieving the messages from savedInstanceState
        messages = getInput(savedInstanceState);
        mAdapter = new MessageListAdapter(messages, this);
        // setting the adapter to our recyclerview obj
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // attaching the send button to insert a new message to the adapter
        btn = findViewById(R.id.sendButton);
        // to avoid sending empty message, we set the button enable to false at start
        btn.setEnabled(false);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addMessage(new ChatMessage(mInput.getText().toString(),
                        System.currentTimeMillis(), "Imri Luzzattov"));
                // clearing input text
                mInput.setText("");
            }
        });
        mInput = findViewById(R.id.edittext_chatbox);
        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                btn.setEnabled(!TextUtils.isEmpty(s));
            }
        });

    }

    private ArrayList<ChatMessage> getInput(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return new ArrayList<>();
        }
        if (savedInstanceState.getStringArrayList(MESSAGES) == null) {
            return new ArrayList<>();
        }
        ArrayList<ChatMessage> output = new ArrayList<>();
        for (String singleMessage : Objects.requireNonNull(savedInstanceState.getStringArrayList(MESSAGES))) {
            try {
                ChatMessage msg = new ChatMessage(singleMessage);
                output.add(msg);
            } catch (Exception e) {
                Log.e("MessageListActivity", e.getMessage());
            }
        }
        return output;
    }

    @Override
    public void onClick(ChatMessage message) {
        MessageDetailsFragment frag = MessageDetailsFragment.newInstance(message);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame, frag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMessageDeleted(ChatMessage msg) {
        mAdapter.removeItem(msg);
        getSupportFragmentManager().popBackStack();
    }


}