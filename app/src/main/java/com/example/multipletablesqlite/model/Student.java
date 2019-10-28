package com.example.multipletablesqlite.model;

public class Student {
    private long id;
    private String nim;
    private String name;
    private String phone;
    private String email;

    public Student(long id, String nim, String name, String phone, String email){
        this.id = id;
        this.name = name;
        this.nim = nim;
        this.phone = phone;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
