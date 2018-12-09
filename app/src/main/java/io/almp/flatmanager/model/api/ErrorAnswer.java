package io.almp.flatmanager.model.api;

public class ErrorAnswer {

    private boolean error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public ErrorAnswer() {
    }

    /**
     * @param error
     */
    public ErrorAnswer(boolean error, String message) {
        super();
        this.error = error;
        this.message=message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }


}
