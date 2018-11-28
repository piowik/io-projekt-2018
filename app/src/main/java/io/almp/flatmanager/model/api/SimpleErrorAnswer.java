package io.almp.flatmanager.model.api;

public class SimpleErrorAnswer {

    private boolean error;

    public SimpleErrorAnswer() {
    }

    /**
     * @param error
     */
    public SimpleErrorAnswer(boolean error) {
        super();
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }


}
