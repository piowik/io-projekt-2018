package io.almp.flatmanager.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.almp.flatmanager.model.Message;

/**
 *  Class containing methods required to check and answer properly on messages tab.
 */

class MessagesAnswer {
    @SerializedName("messages")
    @Expose
    private List<Message> mMessages;

}
