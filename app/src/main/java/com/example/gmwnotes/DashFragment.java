package com.example.gmwnotes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

public class DashFragment extends Fragment implements View.OnClickListener {

    Button btnHorario, btnData, btnSave;
    Calendar calendarToSave;
    DatabaseHelper databaseHelper;
    private ListView listaView;
    private EditText tarefaNome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listaView = (ListView) view.findViewById(R.id.listaTarefa);
        databaseHelper = new DatabaseHelper(this.getContext());
        Button salvar = view.findViewById(R.id.savebtn);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
        Button atualizar = view.findViewById(R.id.refreshList);
        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listar();
            }
        });
        btnData = view.findViewById(R.id.databtn);
        btnHorario = view.findViewById(R.id.horariobtn);
        btnSave = view.findViewById(R.id.savebtn);
        btnData.setOnClickListener(this);
        btnHorario.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        calendarToSave = Calendar.getInstance();
        tarefaNome =  (EditText) view.findViewById(R.id.nomeaula);
        listar();
    }

    @Override
    public void onClick(View view) {
        if(view == btnData){
            selectData();
        }else if(view == btnHorario){
            selectHorario();
        }else{
            salvar();
        }
    }
    private void salvar(){
        String name = tarefaNome.getText().toString();
        String data = (btnData.getText().toString().trim());
        String hora = (btnHorario.getText().toString().trim());
        String tarefa = name + " / " + data + " / " + hora;
        boolean insertData = databaseHelper.addData(tarefa);
        if(data != "Selecione a data" || hora != "Selecione o horario" || !TextUtils.isEmpty(tarefaNome.getText().toString())){
            if(insertData){
                toast("Tarefa adicionada com sucesso");
            }else{
                toast("Erro: Não foi possível adicionar tarefa");
            }
            listar();
        }else{
            toast("Por favor adicione valores válidos");
        }

    }
    private void selectHorario(){
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                btnHorario.setText(formatTime(i,i1));
                calendarToSave.set(Calendar.HOUR_OF_DAY,i);
                calendarToSave.set(Calendar.MINUTE,i1);
            }
        }, hora,minuto, false);
        timePickerDialog.show();
    }
    private void selectData(){
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                btnData.setText(dia+"-"+mes+"-"+ano);
                calendarToSave.set(Calendar.DAY_OF_MONTH, dia);
                calendarToSave.set(Calendar.MONTH, mes);
                calendarToSave.set(Calendar.YEAR, ano);

            }
        },ano, mes, dia);
        datePickerDialog.show();
    }

    public void listar(){
        Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this.getContext(),android.R.layout.simple_list_item_1, listData);
        listaView.setAdapter(adapter);

        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String nome = adapterView.getItemAtPosition(i).toString();
                Cursor data = databaseHelper.getItemID(nome);
                int itemID = 1;
                while (data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Intent editarTarefa = new Intent(DashFragment.this.getContext(), EditarTarefa.class);
                    editarTarefa.putExtra("id", itemID);
                    editarTarefa.putExtra("nome", nome);
                    startActivity(editarTarefa);
                }
            }
        });
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

    private void toast(String message){
        Toast.makeText(this.getContext(),message, Toast.LENGTH_SHORT).show();
    }
}
