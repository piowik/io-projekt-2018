package io.almp.flatmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RentHistoryItem {
    @SerializedName("rent_value")
    @Expose
    private float mTotalValue;
    @SerializedName("per_person")
    @Expose
    private float mValuePerPerson;
    @SerializedName("rent_date")
    @Expose
    private String mDate;

    public RentHistoryItem(float totalValue, float valuePerPerson, String date) {
        this.mTotalValue = totalValue;
        this.mValuePerPerson = valuePerPerson;
        this.mDate = date;
    }

    public String getDate() {
        return mDate;
    }

    public float getTotalValue() {
        return mTotalValue;
    }

    public float getValuePerPerson() {
        return mValuePerPerson;
    }
}