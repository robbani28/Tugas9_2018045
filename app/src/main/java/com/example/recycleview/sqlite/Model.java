package com.example.recycleview.sqlite;

public class Model {
    private int id;
    private byte[]avatar;
    private String title;
    private String star;
    private String price;
    private String about;

    public Model(int id, byte[] avatar, String title, String star, String price, String about) {
        this.id = id;
        this.avatar = avatar;
        this.title = title;
        this.star = star;
        this.price = price;
        this.about = about;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public byte[] getAvatar() {
        return avatar;
    }
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getStar() {
        return star;
    }
    public void setStar(String star) {
        this.star = star;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }
}
