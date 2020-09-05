package com.example.gameconnect3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//  android:background="#e5ff99"
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListenerOnButton();
    }

    public void setListenerOnButton() {
        Button b1 = (Button) findViewById(R.id.normalModeButton);
        Button b2 = (Button) findViewById(R.id.ghostModeButton);
        //Button b3 = (Button) findViewById(R.id.computerAI);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".Classicmodeactivity");
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".ghostmode");
                startActivity(intent);
            }
        });
        /*b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(".Computer");
                startActivity(intent);
            }
        });*/
    }
}
