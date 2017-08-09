package com.example.mckoy.itemsharing;


import com.google.firebase.database.DataSnapshot;

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

    public Item(DataSnapshot itemSnapshot) {
        itemName = itemSnapshot.child("mItemName").getValue(String.class);
        sellerName = itemSnapshot.child("mSellerName").getValue(String.class);
        address = itemSnapshot.child("mAddress").getValue(String.class);
        phoneNumber = itemSnapshot.child("mPhoneNumber").getValue(String.class);
        rating = itemSnapshot.child("mRating").getValue(String.class);
        description = itemSnapshot.child("mDescription").getValue(String.class);
        photourl = itemSnapshot.child("mPhotourl").getValue(String.class);
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
