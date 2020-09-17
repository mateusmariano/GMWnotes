package com.example.gmwnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class CalcMedia extends AppCompatActivity {

    final EditText av1text = findViewById(R.id.av1textfield);
    final EditText av2text = findViewById(R.id.av2textfield);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_media);
    }
    private Double Calcular(){
       Double resultado = 0.0;
       Double faltante = 0.0;

        return resultado;
    }
}