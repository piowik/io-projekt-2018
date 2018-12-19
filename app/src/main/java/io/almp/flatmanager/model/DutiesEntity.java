package io.almp.flatmanager.model;

public class DutiesEntity {
    private Integer duty_id;
    private Integer flat_id;
    private Integer user_id;
    private String value;
    private String duty_name;

    public Integer getDuty_id() {
        return duty_id;
    }

    public void setDuty_id(Integer duty_id) {
        this.duty_id = duty_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getFlat_id() {
        return flat_id;
    }

    public void setFlat_id(Integer flat_id) {
        this.flat_id = flat_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDuty_name() {
        return duty_name;
    }

    public void setDuty_name(String duty_name) {
        this.duty_name = duty_name;
    }


    public DutiesEntity(Integer duty_id, Integer flat_id, Integer user_id, String value, String duty_name) {
        this.duty_id = duty_id;
        this.flat_id = flat_id;
        this.user_id = user_id;
        this.value = value;
        this.duty_name = duty_name;
    }
}
