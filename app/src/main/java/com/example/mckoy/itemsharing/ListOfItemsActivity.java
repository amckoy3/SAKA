package com.example.mckoy.itemsharing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ListOfItemsActivity extends AppCompatActivity{

    private Button mButton;
    private List<Item> mItems;
    private ListView mListView;


    //Log out button
    private Button mLogoutButton;

    //Firebase variables needed for logout
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    private static final int POST_ITEM_ACTIVITY =  5;
    private String mQuery ;
    public final static String mQueryKey = "query";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_items);
        Toast.makeText(ListOfItemsActivity.this, "Login is successful",
                Toast.LENGTH_SHORT).show();
        mListView = (ListView) findViewById(R.id.list_view);
        ItemDataSource.get(ListOfItemsActivity.this).getItems("",new ItemDataSource.ItemListener() {
            @Override
            public void onItemsReceived(List<Item> items) {
                mItems = items;
                mListView.setAdapter(new ItemAdapter(ListOfItemsActivity.this, R.layout.list_view_item, items));
            }
        });
        mButton = (Button) findViewById(R.id.button_id);

        //initializing Firebase instance variables

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(ListOfItemsActivity.this, MainActivity.class));
                }
            }
        };
        mLogoutButton = (Button) findViewById(R.id.log_out);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Firebase signout
                mAuth.signOut();

                //Google API signout
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);

            }
        });


        //when any rows of items are clicked
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object o = mListView.getItemAtPosition(position);
                Item item = (Item)o;
                //Toast.makeText(ListOfItemsActivity.this, "You have chosen : " + " " + item.getItemName(), Toast.LENGTH_LONG).show();

                //this will call ItemDetailsActivity
                Intent i = new Intent(ListOfItemsActivity.this, ItemDetailsActivity.class);
                i.putExtra("itemObject", item);
                startActivity(i);
            }
        });


        mButton.setOnClickListener(buttonClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POST_ITEM_ACTIVITY) {
            finish();
        }
    }

    public void refresh() {
        ItemDataSource.get(ListOfItemsActivity.this).getItems("", new ItemDataSource.ItemListener() {
            @Override
            public void onItemsReceived(List<Item> items) {
                mItems = items;
                mListView.setAdapter(new ItemAdapter(ListOfItemsActivity.this, R.layout.list_view_item, items));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.app_search_bar);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_search_bar:
                AlertDialog.Builder bObject = new AlertDialog.Builder(this);
                final EditText textInput = new EditText(this);
                textInput.setInputType(InputType.TYPE_CLASS_TEXT);
                bObject.setView(textInput);
                bObject.setTitle(R.string.title)
                        //.setCancelable(false)
                        .setPositiveButton(R.string.positive_text, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dInterface, int x) {
                                mQuery = textInput.getText().toString();
                                getSupportFragmentManager().beginTransaction().replace(R.id.container
                                        , createCustomFragment(new SearchFragment(), mQuery)).commit();

                            }
                        })
                        .setNegativeButton(R.string.negative_text, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dInterface, int x) {
                                dInterface.cancel();
                            }
                        });
                AlertDialog dialog = bObject.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private Fragment createCustomFragment(Fragment fragment, String query){
        Bundle bundle = new Bundle();
        bundle.putString(mQueryKey,query);
        fragment.setArguments(bundle);
        return fragment;
    }


    private View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v){
            //Intent i = new Intent(ListOfItemsActivity.this, PostItemActivity.class);
            //startActivity(i);
            startActivityForResult(new Intent(ListOfItemsActivity.this, PostItemActivity.class), POST_ITEM_ACTIVITY);
            finish();
        }
    };
}
