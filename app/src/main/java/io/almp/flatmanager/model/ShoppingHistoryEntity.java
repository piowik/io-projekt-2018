package io.almp.flatmanager.model;

public class ShoppingHistoryEntity {
    private Integer item_id;
    private Integer flat_id;
    private String name;
    private String item_name;
    private double price;
    private String purchase_date;

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getFlat_id() {
        return flat_id;
    }

    public void setFlat_id(Integer flat_id) {
        this.flat_id = flat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public ShoppingHistoryEntity(Integer item_id, Integer flat_id, String name, String item_name, double price, String purchase_date) {

        this.item_id = item_id;
        this.flat_id = flat_id;
        this.name = name;
        this.item_name = item_name;
        this.price = price;
        this.purchase_date = purchase_date;
    }
}
