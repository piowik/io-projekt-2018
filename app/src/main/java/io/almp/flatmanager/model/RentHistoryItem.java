package io.almp.flatmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *  Class containing methods required to create entity of items in rent history.
 */

public class RentHistoryItem {
    @SerializedName("rent_value")
    @Expose
    private final float mTotalValue;
    @SerializedName("per_person")
    @Expose
    private final float mValuePerPerson;
    @SerializedName("rent_date")
    @Expose
    private final String mDate;

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