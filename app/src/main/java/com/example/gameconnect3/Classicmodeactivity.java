package com.example.gameconnect3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Classicmodeactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classicmodeactivity);
        setListenerOnButton();
    }
    public void setListenerOnButton() {
        Button b1 = (Button) findViewById(R.id.classic_one);
        Button b2 = (Button) findViewById(R.id.classic_ai);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".Classicactivityone");
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".Classicactivityghost");
                startActivity(intent);
            }
        });
    }
}
