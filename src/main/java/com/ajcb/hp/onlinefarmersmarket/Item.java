package com.ajcb.hp.onlinefarmersmarket;

public class Item {

    String np;
    String add;
    String sto;
    String dt;

    public Item(String np,String add,String sto,String dt)
    {
        this.np=np;
        this.add=add;
        this.sto=sto;
        this.dt=dt;
    }
    public String getNp()
    {
        return np;
    }
    public String getAdd()
    {
        return add;
    }
    public String getSto(){return sto;}
    public String getDt(){return dt;}
}
