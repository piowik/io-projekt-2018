package io.almp.flatmanager.model.api;

/**
 *  Class containing methods required to answer properly on join flat tab.
 */

public class JoinFlatAnswer {

    private boolean error;
    private String message;
    private int flat;

    public JoinFlatAnswer() {
    }

    /**
     * @param error
     * @param message
     * @param flat
     */
    public JoinFlatAnswer(boolean error, String message, int flat) {
        super();
        this.error = error;
        this.message = message;
        this.flat = flat;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getFlatId() {
        return flat;
    }
}
