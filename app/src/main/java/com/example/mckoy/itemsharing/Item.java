package com.example.mckoy.itemsharing;


public class Item {
    String itemName;
    String sellerName;
    String address;
    String phoneNumber;
    String rating;
    String description;
    String photourl;

    public Item(String itemname, String sellerName, String address, String phoneNumber, String rating, String description, String photoUrl) {
        this.itemName = itemname;
        this.sellerName = sellerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.description = description;
        this.photourl = photoUrl;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotourl() {
        return photourl;
    }
}
