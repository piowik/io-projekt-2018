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

    public LoginAnswer() {
    }

    /**
     * @param message
     * @param error
     * @param token
     * @param user_id
     * @param flat
     * @param invitationCode
     */
    public LoginAnswer(boolean error, String token, String message, long user_id, Integer flat, String invitationCode) {
        super();
        this.error = error;
        this.token = token;
        this.message = message;
        this.user_id=user_id;
        this.flat = flat;
        this.invitationCode = invitationCode;
    }

    public boolean isError() {
        return error;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public long getId() {
        return user_id;
    }

    public void setId(long id) {
        this.user_id = id;
    }

    public int getFlatId() {
        return flat;
    }

}
