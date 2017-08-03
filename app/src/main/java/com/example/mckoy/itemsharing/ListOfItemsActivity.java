package com.example.mckoy.itemsharing;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ListOfItemsActivity extends AppCompatActivity{

    //private TextView mWelcomeMessage;
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_items);
        //mWelcomeMessage = (TextView) findViewById(R.id.welcome);
        Toast.makeText(ListOfItemsActivity.this, "Login is successful",
                Toast.LENGTH_SHORT).show();
        mButton = (Button) findViewById(R.id.button_id);

        Log.i("aa", "Welcome home");
        //mWelcomeMessage.setText("Welcome to the homepage!");


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
