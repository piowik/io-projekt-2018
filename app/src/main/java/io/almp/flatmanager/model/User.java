package io.almp.flatmanager.model;

public class User {
    private Integer user_id;
    private Integer flat_id ;
    private String login;
    private String name;
    private String email;
    private double balance;

    public User(){}

    public User(Integer id, Integer flatId, String login, String name, String email, double balance) {
        this.user_id = id;
        this.flat_id = flatId;
        this.login = login;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public Integer getId() {
        return user_id;
    }

    public void setId(Integer id) {
        this.user_id = id;
    }

    public Integer getFlatId() {
        return flat_id;
    }

    public void setFlatId(Integer flatId) {
        this.flat_id = flatId;
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
