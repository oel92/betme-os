package com.example.betme;



import java.util.ArrayList;

public class Group {

    private String Name;
    private ArrayList<Object> Items;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<Object> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Object> ch_list) {
        this.Items = ch_list;
    }

}
