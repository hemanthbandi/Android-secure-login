package com.example.hemanth.FinalProject;

/**
 * Created by hemanth  on 7/2/17.
 */
public class Contact {
    private String name;
    public Contact(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    private String email;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
