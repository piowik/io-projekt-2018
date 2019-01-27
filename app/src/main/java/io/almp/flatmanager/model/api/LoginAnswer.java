package io.almp.flatmanager.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *  Class containing methods required to check and answer properly on logging tab.
 */


public class LoginAnswer {

    private boolean error;
    private String token;
    private int flat;
    private String message;
    @SerializedName("invitation_code")
    @Expose
    private String invitationCode;
    private long user_id;

    public boolean isError() {
        return error;
    }

    public String getToken() {
        return token;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public long getId() {
        return user_id;
    }

    public int getFlatId() {
        return flat;
    }

}
