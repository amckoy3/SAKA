package com.example.mckoy.itemsharing;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListOfItemsActivity extends AppCompatActivity{

    private Button mButton;
    private List<Item> mItems;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_items);
        Toast.makeText(ListOfItemsActivity.this, "Login is successful",
                Toast.LENGTH_SHORT).show();
        mListView = (ListView) findViewById(R.id.list_view);
        ItemDataSource.get(ListOfItemsActivity.this).getItems(new ItemDataSource.ItemListener() {
            @Override
            public void onItemsReceived(List<Item> items) {
                mItems = items;
                mListView.setAdapter(new ItemAdapter(ListOfItemsActivity.this, R.layout.list_view_item, items));
            }
        });
        mButton = (Button) findViewById(R.id.button_id);



        mButton.setOnClickListener(buttonClickListener);
    }



    private View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v){
            Intent i = new Intent(ListOfItemsActivity.this, PostItemActivity.class);
            startActivity(i);
        }
    };
}
