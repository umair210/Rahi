package com.example.rahipart2.Model;

public class products
{
private String pid, date, time, itineary, image, noofdays, location;

public products()
{

}

    public products(String pid, String date, String time, String itineary, String image, String noofdays, String location)
    {
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.itineary = itineary;
        this.image = image;
        this.noofdays = noofdays;
        this.location = location;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getItineary() {
        return itineary;
    }

    public void setItineary(String itineary) {
        this.itineary = itineary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNoofdays() {
        return noofdays;
    }

    public void setNoofdays(String noofdays) {
        this.noofdays = noofdays;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
