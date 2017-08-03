package com.example.mckoy.itemsharing;


public class Item {
    String mName;
    String mAddress;
    String mPhoneNumber;
    String mRating;
    String mDescription;

    public Item(String name, String address, String phoneNumber, String rating, String description) {
        this.mName = name;
        this.mAddress = address;
        this.mPhoneNumber = phoneNumber;
        this.mRating = rating;
        this.mDescription = description;
    }

    public String getmName() {
        return mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmDescription() {
        return mDescription;
    }
}
