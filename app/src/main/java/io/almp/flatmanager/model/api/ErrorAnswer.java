package io.almp.flatmanager.model.api;

/**
 * Class containing methods required to check and answer error if found.
 */


public class ErrorAnswer {

    private boolean error;
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }
}
