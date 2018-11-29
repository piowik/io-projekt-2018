package io.almp.flatmanager.model;

public class User {
    private long id;
    private long flat_id;
    private String login;
    private String name;
    private String email;
    private double balance;

    public User(long id, long flat_id, String login, String name, String email, double balance) {
        this.id = id;
        this.flat_id = flat_id;
        this.login = login;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFlat_id() {
        return flat_id;
    }

    public void setFlat_id(long flat_id) {
        this.flat_id = flat_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
