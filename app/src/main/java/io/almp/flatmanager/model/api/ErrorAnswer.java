package io.almp.flatmanager.model.api;

/**
 *  Class containing methods required to check and answer error if found.
 */


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
     * @param error returns true if an error occurred
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
