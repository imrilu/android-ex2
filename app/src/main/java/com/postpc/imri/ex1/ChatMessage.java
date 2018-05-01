package com.postpc.imri.ex1;

import android.content.Context;

/**
 * Created by Imri on 16-Apr-18.
 */

public class ChatMessage {

    String message;
    long timeStamp;
    String senderName;

    ChatMessage(String wholeMsg) {
        String[] temp = wholeMsg.split("\n");
        this.senderName = temp[0];
        this.timeStamp = Long.valueOf(temp[1]);
        this.message = temp[2];
    }

    ChatMessage(String message, long timeStamp, String senderName) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.senderName = senderName;
    }


    protected String getMessage() {
        return message;
    }

    protected long getTimeStamp() {
        return timeStamp;
    }

    protected String getSenderName() { return senderName; }

    public String toString() {
        return senderName + '\n' + timeStamp + '\n' + message;
    }
}
