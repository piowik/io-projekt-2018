package io.almp.flatmanager;

class UserBalance {
    private String userId;
    private String userName;
    private double balance;

    public UserBalance(String userId, String userName, double balance) {
        this.userId = userId;
        this.userName = userName;
        this.balance = balance;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
