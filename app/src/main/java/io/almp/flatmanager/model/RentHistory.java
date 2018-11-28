package io.almp.flatmanager.model;

public class RentHistory {
    private String mDate;
    private float mValue;

    public RentHistory(String date, float value) {
        this.mDate = date;
        this.mValue = value;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float value) {
        mValue = value;
    }
}