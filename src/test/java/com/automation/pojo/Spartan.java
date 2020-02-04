package com.automation.pojo;

import com.google.gson.annotations.SerializedName;

public class Spartan {

    @SerializedName("id")
    private int spartanId;
    private String name;
    private String gender;
    private long phone;

    public Spartan() {
    }

    public Spartan(int spartanId, String name, String gender, long phone) {
        this.spartanId = spartanId;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

    public int getSpartanId() {
        return spartanId;
    }

    public void setSpartanId(int spartanId) {
        this.spartanId = spartanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhone(long l) {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Spartan{" +
                "spartanId=" + spartanId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }

    public Spartan withPhone(long phone) {
        this.phone = phone;
        return this;
    }
    public Spartan withName(String name) {
        this.name = name;
        return this;
    }
    public Spartan withGender(String gender) {
        this.gender = gender;
        return this;
    }
}
