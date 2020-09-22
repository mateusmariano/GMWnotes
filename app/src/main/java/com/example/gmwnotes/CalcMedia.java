package com.example.gmwnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalcMedia extends AppCompatActivity{

    final double corte = 7;
    double av1Value = 0.0;
    double av2Value = 0.0;
    double av3Value = 0.0;
    double finalResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_media);

        final EditText av1text = findViewById(R.id.av1textfield);
        final EditText av2text = findViewById(R.id.av2textfield);
        final EditText av3text = findViewById(R.id.av3TextField);
        final TextView faltantetext = findViewById(R.id.faltanteText);
        final Button calcbutton = findViewById(R.id.calcButton);
        calcbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faltantetext.setText("");
                if (!TextUtils.isEmpty(av1text.getText().toString()) && !TextUtils.isEmpty(av2text.getText().toString())) {
                    if (TextUtils.isEmpty(av3text.getText().toString())) {
                        av1Value = Double.parseDouble(av1text.getText().toString());
                        av2Value = Double.parseDouble(av2text.getText().toString());
                    } else {
                        av1Value = Double.parseDouble(av1text.getText().toString());
                        av2Value = Double.parseDouble(av2text.getText().toString());
                        av3Value = Double.parseDouble(av3text.getText().toString());
                    }
                    finalResultado = Calcular(av1Value, av2Value, av3Value, faltantetext, av3text);
                    if (finalResultado == 0) {
                        faltantetext.setText("Você já passou!");
                    } else {
                        faltantetext.setText(Double.toString(finalResultado));
                    }
                }else{
                    faltantetext.setText("Adicione os valores nos campos");
                }

            }
        });
    }

    private double Calcular(double av1Value, double av2Value, double av3Value, TextView faltantetext, TextView av3text) {
        double resultado;
        double faltante;
        double maiorNota;
        boolean temAv3 = false;
        boolean passou = false;

        temAv3 = checkAv3(av3text);
        maiorNota = getMaiorNota(temAv3, av1Value, av2Value, av3Value);

        if (!temAv3) {
            resultado = (av1Value + av2Value) / 2;
        } else {
            resultado = (av1Value + av2Value + av3Value) / 3;
        }
        faltante = (corte * 2) - maiorNota;
        passou = checkPassou(resultado);
        if (passou) {
            return 0;
        }else{
            return faltante;
        }
    }

    private boolean checkAv3(TextView av3text) {
        if (TextUtils.isEmpty(av3text.getText().toString())) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkPassou(double resultado) {
        if (resultado > corte) {
            return true;
        } else {
            return false;
        }
    }

    private double getMaiorNota(boolean _temAv3, double av1Value, double av2Value, double av3Value) {
        if (_temAv3) {
            if (av1Value > av2Value && av1Value > av3Value) {
                return av1Value;
            } else if (av2Value > av1Value && av2Value > av3Value) {
                return av2Value;
            } else {
                return av3Value;
            }
        } else {
            if (av1Value > av2Value) {
                return av1Value;
            } else {
                return av2Value;
            }
        }
    }
}
