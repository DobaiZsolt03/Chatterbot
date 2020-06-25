package com.example.chatterbot.Details;

public class Contact {
    private String Username, Email;

    public Contact(String Username) {
        this.Username = Username;
        this.Email = Email;
    }

    public Contact(){

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }
}
