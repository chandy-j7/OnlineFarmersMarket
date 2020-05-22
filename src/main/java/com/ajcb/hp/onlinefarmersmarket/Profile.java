package com.ajcb.hp.onlinefarmersmarket;

public class Profile {
    private String expiry;
    private String name;
    private String description;
    private String uid;
    private String time;
    private String stocks;
    private String price;
    private String DeliveryType;
    private String selfaddress;
    private String selfphone;
    private String randomID;

    public Profile() {
    }

    public Profile(String expiry, String name, String description,String uid,String time,String stocks,String price,String DeliveryType,String selfaddress,String selfphone,String randomID) {
        this.expiry = expiry;
        this.name = name;
        this.description = description;
        this.uid = uid;
        this.time = time;
        this.stocks = stocks;
        this.price = price;
        this.DeliveryType = DeliveryType;
        this.selfaddress = selfaddress;
        this.selfphone = selfphone;
        this.randomID = randomID;
    }

    public static int size() {
        return 0;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }


    public String getDeliveryType() {
        return DeliveryType;
    }

    public void setDeliveryType(String expiry) {
        this.DeliveryType = DeliveryType;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }




    public String getSelfaddress() {
        return selfaddress;
    }

    public void setSelfaddress(String selfaddress) {
        this.selfaddress = selfaddress;
    }
    public String getSelfphone() {
        return selfphone;
    }

    public void setSelfphone(String selfphone) {
        this.selfphone=selfphone;
    }


    public String getRandomid() {
        return randomID;
    }

    public void getRandomid(String randomID) {
        this.randomID = randomID;
    }
}

