package com.ajcb.hp.onlinefarmersmarket;

public class BuyerProfile {
    private String address;
    private String names;
    private String stoc;
    private String dates;

    public BuyerProfile() {
    }

    public BuyerProfile(String address, String names,String stoc,String dates) {
        this.address = address;
        this.names = names;
        this.stoc = stoc;
        this.dates = dates;
    }

    public static int size() {
        return 0;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getStoc() {
        return stoc;
    }

    public void setStoc(String stoc) {
        this.stoc = stoc;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

}

