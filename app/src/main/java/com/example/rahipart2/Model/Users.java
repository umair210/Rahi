package com.example.rahipart2.Model;

public class Users
{
    private String email, password, username, image, bioofuser;

    private Users()
    {}

    public Users(String email, String password, String username, String image, String bioofuser) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.image = image;
        this.bioofuser = bioofuser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBioofuser() {
        return bioofuser;
    }

    public void setBioofuser(String bioofuser) {
        this.bioofuser = bioofuser;
    }
}
