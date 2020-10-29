package com.example.gmwnotes;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.gmwnotes.Database.DatabaseClass;
import com.example.gmwnotes.Database.EntityClass;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static androidx.core.content.ContextCompat.getSystemService;

public class DashFragment extends Fragment implements View.OnClickListener {

    Button btnHorario, btnData, btnSave;
    String quandoNotificar;
    DatabaseClass databaseClass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button button2 = view.findViewById(R.id.notButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });
        btnData = view.findViewById(R.id.databtn);
        btnHorario = view.findViewById(R.id.horariobtn);
        btnSave = view.findViewById(R.id.savebtn);
        btnData.setOnClickListener(this);
        btnHorario.setOnClickListener(this);
        btnSave.setOnClickListener(this);
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
    public void showNotification(){
        String welcome = "Notificação enviada" ;
        Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "testNot");
        builder.setContentTitle("Sua aula vai começar!");
        builder.setContentText("Não se atrase, sua aula vai começar daqui a pouco");
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_24);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
        managerCompat.notify(1,builder.build());
    }

    private void salvar(){
        EntityClass entityClass = new EntityClass();
        String name = "aula";
        String data = (btnData.getText().toString().trim());
        String hora = (btnHorario.getText().toString().trim());
        entityClass.setEventdate(data);
        entityClass.setEventname(name);
        entityClass.setEventtime(hora);
        databaseClass.EventDao().insertAll(entityClass);
        setAlarm(name, data, hora);

        //finish();
    }
    private void selectHorario(){
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                quandoNotificar = i+":"+i1;
                btnHorario.setText(formatTime(i,i1));
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
    private void setAlarm(String text, String date, String time){
        AlarmManager am = (AlarmManager)this.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmBroadcast.class);
        intent.putExtra("event", text);
        intent.putExtra("horario", date);
        intent.putExtra("data", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getContext(), 0,intent,PendingIntent.FLAG_ONE_SHOT);
        String dataEHora = date+ " " + quandoNotificar;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try{
            Date date1 = formatter.parse(dataEHora);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(),pendingIntent);


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}
