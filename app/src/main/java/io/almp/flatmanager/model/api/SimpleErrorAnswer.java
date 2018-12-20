package io.almp.flatmanager.model.api;

/**
 *  Class containing methods required to check and answer properly on short errors.
 */

public class SimpleErrorAnswer {

    private boolean error;

    public SimpleErrorAnswer() {
    }

    /**
     * @param error returns true if an error occurred
     */
    public SimpleErrorAnswer(boolean error) {
        super();
        this.error = error;
    }

    public boolean isError() {
        return error;
    }


}
