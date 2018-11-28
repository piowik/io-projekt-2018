package io.almp.flatmanager.model.api;

public class LoginAnswer {

    private boolean error;
    private String token;
    private String message;

    public long getId() {
        return user_id;
    }

    public void setId(long id) {
        this.user_id = id;
    }

    private long user_id;

    public LoginAnswer() {
    }

    /**
     * @param message
     * @param error
     * @param token
     * @param user_id
     */
    public LoginAnswer(boolean error, String token, String message, long user_id) {
        super();
        this.error = error;
        this.token = token;
        this.message = message;
        this.user_id=user_id;

    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
