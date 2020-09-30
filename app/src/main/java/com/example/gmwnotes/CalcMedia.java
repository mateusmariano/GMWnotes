package com.example.gmwnotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalcMedia extends Fragment {

    final double corte = 7;
    double av1Value = 0.0;
    double av2Value = 0.0;
    double av3Value = 0.0;
    double finalResultado;
    double media = 0.0;
    int maiorIndex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_calc_media, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final EditText av1text = view.findViewById(R.id.av1textfield);
        final EditText av2text = view.findViewById(R.id.av2textfield);
        final EditText av3text = view.findViewById(R.id.av3TextField);
        final TextView faltantetext = view.findViewById(R.id.faltanteText);
        final TextView mediatext = view.findViewById(R.id.mediatxt);
        final Button calcbutton = view.findViewById(R.id.calcButton);
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
                    faltantetext.setText(" ");
                    mediatext.setText(" ");
                    finalResultado = Calcular(av1Value, av2Value, av3Value, faltantetext, av3text);
                    if (finalResultado == 0) {
                        faltantetext.setText("Você já passou!");
                    } else {
                        if(checkAv3(av3text)){
                            faltantetext.setText("Você não passou por "+Double.toString(finalResultado)+" pontos");
                            mediatext.setText("Sua media final foi "+Double.toString(media));
                        }
                        else {
                            faltantetext.setText("Você precisa de "+ Double.toString(finalResultado)+" pontos");
                            mediatext.setText("Sua media por enquanto é "+Double.toString(media));
                        }

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
        double segundaMaiorNota;
        boolean temAv3 = false;
        boolean passou = false;

        temAv3 = checkAv3(av3text);
        maiorNota = getMaiorNota(temAv3, av1Value, av2Value, av3Value);

        if (!temAv3) {
            resultado = (av1Value + av2Value) / 2;
        } else {
            segundaMaiorNota = getSegMaiorNota(av1Value,av2Value,av3Value);
            resultado = (maiorNota + segundaMaiorNota) / 2;
        }
        media = resultado;
        faltante = (corte * 2) - maiorNota;
        passou = checkPassou(resultado);
        if (passou) {
            return 0;
        }else{
            return faltante;
        }
    }

    private boolean checkAv3(TextView av3text) {
        if (TextUtils.isEmpty(av3text.getText().toString()) || av3text.getText() == "0" || av3text.getText() == "0.0") {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkPassou(double resultado) {
        if (resultado >= corte) {
            return true;
        } else {
            return false;
        }
    }

    private double getMaiorNota(boolean _temAv3, double av1Value, double av2Value, double av3Value) {
        if (_temAv3) {

            if (av1Value > av2Value && av1Value > av3Value || av1Value == av2Value) {
                maiorIndex = 0;
                return av1Value;
            } else if (av2Value > av1Value && av2Value > av3Value) {
                maiorIndex = 1;
                return av2Value;
            } else {
                maiorIndex = 2;
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
    private double getSegMaiorNota(double av1, double av2, double av3){
        double seg = 0.0;
        switch (maiorIndex){
            case 0:
                if(av1 == av2){
                    seg = av2;
                }else if(av1 == av3){
                    seg = av3;
                }else {
                    if (av2 > av3) {
                        seg = av2;
                    } else {
                        seg = av3;
                    }
                }
                break;
            case 1:
                if(av2 == av1){
                    seg = av1;
                }else if(av2 == av3){
                    seg = av3;
                }else {
                    if (av1 > av3) {
                        seg = av1;
                    } else {
                        seg = av3;
                    }
                }
                break;
            case 2:
                if(av3 == av1){
                    seg = av1;
                }else if(av3 == av2){
                    seg = av2;
                }else {
                    if (av1 > av2) {
                        seg = av1;
                    } else {
                        seg = av2;
                    }
                }
                break;
        }
        return seg;
    }
}
