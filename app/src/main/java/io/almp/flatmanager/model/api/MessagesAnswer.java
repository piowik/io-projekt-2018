package io.almp.flatmanager.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.almp.flatmanager.model.Message;

/**
 * Created by Mateusz Zaremba on 29.11.2018.
 */
public class MessagesAnswer {
    @SerializedName("messages")
    @Expose
    private List<Message> mMessages;

    public MessagesAnswer(List<Message> messages) {
        mMessages = messages;
    }

    public List<Message> getMessages() {
        return mMessages;
    }

    public void setMessages(List<Message> messages) {
        this.mMessages = messages;
    }
}
