package com.example.tugasakhir;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_table")
public class Data {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String link;

    private String email;

    private int number;

    public Data(String name, String link, String email, int number) {
        this.name = name;
        this.link = link;
        this.email = email;
        this.number = number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getEmail() {
        return email;
    }

    public int getNumber() {
        return number;
    }
}
