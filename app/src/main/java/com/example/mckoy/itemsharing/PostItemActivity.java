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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//this activity is where the seller gives the information of the product he wants to sell
public class PostItemActivity extends AppCompatActivity {

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
    private Button mUploadButton;

    private static final int RC_PHOTO_PICKER =  2;
    Uri filePath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);

        mPostView = (TextView) findViewById(R.id.item_view);
        mPostView.setText("This is where you will give information about the product you want to sell");

        mItemName = (EditText) findViewById(R.id.name_id);
        mAddress = (EditText) findViewById(R.id.address_id);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber_id);
        mRatings = (EditText) findViewById(R.id.rating_id);
        mDescription = (EditText) findViewById(R.id.description_id);
        mChooseImages = (Button) findViewById(R.id.select_picture);
        mUploadButton = (Button) findViewById(R.id.upload_picture);




        //mPostButton = (Button) findViewById(R.id.post_id);


        //initializing firebase storage
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mImagesRef = mStorageRef.child("AppPhotos");

        mChooseImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filePath != null) {
                    StorageReference childRef = mStorageRef.child("image.jpg");

                    //upload the image
                    UploadTask uploadTask = childRef.putFile(filePath);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(PostItemActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(PostItemActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            StorageReference photoRef = mImagesRef.child(selectedImageUri.getLastPathSegment());
            photoRef.putFile(selectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.i("aaa", downloadUrl.toString());
                }
            });
        }
    }

    /*private View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v){
            String name = mItemName.getText().toString();
            String address = mAddress.getText().toString();
            String phoneNumber = mPhoneNumber.getText().toString();
            String rating = mRatings.getText().toString();
            String description = mDescription.getText().toString();

            Item item = new Item(name, address, phoneNumber, rating, description);
            finish();
        }
    };*/
}
