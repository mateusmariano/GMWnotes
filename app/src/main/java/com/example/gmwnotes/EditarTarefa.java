package com.example.gmwnotes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import javax.annotation.Nullable;

public class EditarTarefa extends AppCompatActivity {

    private final static String TAG = "EditarTarefaActivity";
    private Button btnSaveTask, btnDeleteTask, btnHorario, btnData;
    private EditText tarefa, tarefaNome;

    DatabaseHelper databaseHelper;
    private String tarefaSelecionada;
    private int idSelecionado;
    private String[] tarefaSeparada;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        btnSaveTask = (Button) findViewById(R.id.btnSaveTask);
        btnDeleteTask = (Button) findViewById(R.id.btnDeleteTask);
        btnData = (Button) findViewById(R.id.databtn2);
        btnHorario = (Button) findViewById(R.id.horariobtn2);
        tarefa = (EditText) findViewById(R.id.tarefa);
        tarefaNome =  (EditText) findViewById(R.id.nomeaula2);
        databaseHelper = new DatabaseHelper(this);

        Intent recieveIntent = getIntent();
        idSelecionado = recieveIntent.getIntExtra("id", -1);
        tarefaSelecionada = recieveIntent.getStringExtra("nome");
        tarefa.setText(tarefaSelecionada);
        separarTarefa();

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectData();
            }
        });
        btnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectHorario();
            }
        });

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarBd();
            }

        });

        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteTask(idSelecionado, tarefaSelecionada);
                tarefa.setText("");
                toast("Removido com sucesso");
            }
        });
    }

    private void salvarBd(){
            tarefa.setText("");
            String item = tarefaNome.getText().toString() + " / " + tarefaSeparada[1] + " / " + tarefaSeparada[2];

            if(!item.equals("")){
                databaseHelper.updateTask(item, idSelecionado, tarefaSelecionada);
                toast("Alteração salva com sucesso");
                tarefa.setText(item);
            }else{
                toast("Preencha a tarefa corretamente");
            }
        }

    private void selectHorario(){
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                tarefaSeparada[2] = formatTime(i,i1);
                btnHorario.setText(tarefaSeparada[2]);

            }
        }, hora,minuto, false);
        timePickerDialog.show();
    }
    private void selectData(){
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                tarefaSeparada[1] = dia+"-"+mes+"-"+ano;
                btnData.setText(tarefaSeparada[1]);

            }
        },ano, mes, dia);
        datePickerDialog.show();
    }

    public String formatTime(int hour, int minute) {

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }

    private void separarTarefa(){
        tarefaSeparada = tarefa.getText().toString().split("/");
        for(int i = 0; i < tarefaSeparada.length; i ++){
            tarefaSeparada[i].trim();
        }
    }

    private void toast(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
