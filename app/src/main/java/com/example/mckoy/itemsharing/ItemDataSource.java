package com.example.mckoy.itemsharing;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDataSource {
    private ImageLoader mImageLoader;

    public interface ItemListener {
        void onItemsReceived(List<Item> items);
    }

    private static ItemDataSource sItemDataSource;
    private Context mContext;

    public static ItemDataSource get(Context context) {
        if (sItemDataSource == null) {
            sItemDataSource = new ItemDataSource(context);
        }
        return sItemDataSource;
    }

    private ItemDataSource(Context context) {
        mContext = context;
    }

    //Firebase methods
    public void getItems(final String query, final ItemListener itemListener) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference itemsRef = databaseRef.child("items");

        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Item> items = new ArrayList<>();
                Iterable<DataSnapshot> iter = dataSnapshot.getChildren();
                for (DataSnapshot itemSnapshot: iter) {
                    String description = itemSnapshot.child("mItemName").getValue(String.class);
                    boolean contains = description.toLowerCase().contains(query.toLowerCase());
                    if(contains == true) {
                        Item item = new Item(itemSnapshot);
                        items.add(item);
                    }
                }
                itemListener.onItemsReceived(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void sendItem(Item item) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference itemsRef = databaseRef.child("items");
        DatabaseReference newItemRef = itemsRef.push();

        Map<String, String> itemValMap = new HashMap<>();
        itemValMap.put("mItemName", item.getItemName());
        itemValMap.put("mSellerName", item.getSellerName());
        itemValMap.put("mPrice", item.getPrice());
        itemValMap.put("mAddress", item.getAddress());
        itemValMap.put("mPhoneNumber", item.getPhoneNumber());
        itemValMap.put("mRating", item.getRating());
        itemValMap.put("mDescription", item.getDescription());
        itemValMap.put("mPhotourl", item.getPhotourl());

        newItemRef.setValue(itemValMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(mContext, "Item has been posted!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}