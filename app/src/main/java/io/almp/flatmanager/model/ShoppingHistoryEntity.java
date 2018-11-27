package io.almp.flatmanager.model;

public class ShoppingHistoryEntity {
    private String name;
    private double cost;
    private String buyer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public ShoppingHistoryEntity(String name, double cost, String buyer) {

        this.name = name;
        this.cost = cost;
        this.buyer = buyer;
    }
}
