package io.almp.flatmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mateusz Zaremba on 29.11.2018.
 */
public class Message {
    @SerializedName("sender_id")
    @Expose
    private long mSenderId;

    public String getSenderName() {
        return mSenderName;
    }

    public void setSenderName(String senderName) {
        mSenderName = senderName;
    }

    @SerializedName("sender_name")
    @Expose
    private String mSenderName;
    @SerializedName("date")
    @Expose
    private String mDate;

    public Message(long senderId, String date, String message,String name) {
        mSenderId = senderId;
        mDate = date;
        mMessage = message;
        mSenderName=name;
    }

    public long getSenderId() {
        return mSenderId;
    }

    public void setSenderId(long senderId) {
        mSenderId = senderId;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    @SerializedName("message")
    @Expose
    private String mMessage;
}
