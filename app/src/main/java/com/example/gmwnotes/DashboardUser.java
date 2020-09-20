package com.example.gmwnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);

        final Button calcButton = findViewById(R.id.calcButton);

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCalcMedia();
            }
        });
    }

    private void goToCalcMedia(){
        Intent intent = new Intent(this, CalcMedia.class);
        startActivity(intent);
    }
}