package com.example.mckoy.itemsharing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.FirebaseUser;

//this activity is where the seller gives the information of the product he wants to sell
public class PostItemActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private TextView mPostView;
    private EditText mItemName;
    private EditText mAddress;
    private EditText mPhoneNumber;
    private EditText mRatings;
    private EditText mDescription;
    private Button mPostButton;

    private StorageReference mStorageRef;
    private StorageReference mImagesRef;
    private FirebaseStorage mStorage;

    private Button mChooseImages;

    private static final int RC_PHOTO_PICKER =  2;
    Uri filePath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);

        //initializing firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        mPostView = (TextView) findViewById(R.id.item_view);
        mPostView.setText("This is where you will give information about the product you want to sell");

        mItemName = (EditText) findViewById(R.id.name_id);
        mAddress = (EditText) findViewById(R.id.address_id);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber_id);
        mRatings = (EditText) findViewById(R.id.rating_id);
        mDescription = (EditText) findViewById(R.id.description_id);
        mChooseImages = (Button) findViewById(R.id.select_picture);

        mPostButton = (Button) findViewById(R.id.post_id);


        //initializing firebase storage
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mImagesRef = mStorageRef.child("AppPhotos");

        mChooseImages.setOnClickListener(new View.OnClickListener() {           //this will launch the choose images window where we can choose images to upload
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        mPostButton.setOnClickListener(new View.OnClickListener() {         //this button, when clicked will create an Item object and send to the Firebase database.
            @Override
            public void onClick(View view) {
                String itemName = mItemName.getText().toString();
                String itemAddress = mAddress.getText().toString();
                String phoneNumber = mPhoneNumber.getText().toString();
                String ratings = mRatings.getText().toString();
                String description = mDescription.getText().toString();
                String sellerName = mFirebaseUser.getDisplayName();         //gets the name of the seller who posted the item
                Item item = new Item(itemName, sellerName, itemAddress, phoneNumber, ratings, description, filePath.toString()); //creates the item object
                ItemDataSource.get(PostItemActivity.this).sendItem(item);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {            //this statement will be invoked as soon as we select the image. We send the image to the Firebase storage here.
            Uri selectedImageUri = data.getData();
            StorageReference photoRef = mImagesRef.child(selectedImageUri.getLastPathSegment());
            photoRef.putFile(selectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    filePath = downloadUrl;
                    Log.i("sdf", downloadUrl.toString());
                    Toast.makeText(PostItemActivity.this, "Upload is successful", Toast.LENGTH_SHORT);
                    //Log.i("aaa", downloadUrl.toString());
                }
            });
        }
    }

}
