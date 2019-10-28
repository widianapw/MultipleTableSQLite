package com.example.multipletablesqlite.model;

public class Prodi {
    private long id;
    private String full_name;
    private String name;

    public Prodi(long id, String full_name, String name){
        this.id =id;
        this.full_name = full_name;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
