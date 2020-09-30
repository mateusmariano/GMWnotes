package com.example.gmwnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        final Button cadbutton = findViewById(R.id.calcButton);
       /* cadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   openText();
            }
        });*/
    }


        public void openText(){
            String welcome = "Cadastrado com sucesso!";
            Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DashboardUser.class);
            startActivity(intent);
        }
    }
