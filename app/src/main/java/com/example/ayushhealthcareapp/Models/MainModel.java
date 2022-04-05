package com.example.ayushhealthcareapp.Models;

import java.util.List;

public class MainModel
{
    int image,distance;
    String name, address, time,website_link,phone_no;
    List<String> services;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public MainModel(){

    }

    public MainModel(int image, String name, String address, String time, String website_link, String phone_no, List<String> services) {
        this.image = image;
        this.name = name;
        this.address = address;
        this.time = time;
        this.website_link = website_link;
        this.phone_no = phone_no;
        this.services = services;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTime() {
        return time;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public List<String> getServices() {
        return services;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }



}
