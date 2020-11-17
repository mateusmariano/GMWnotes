package com.example.gmwnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import javax.annotation.Nullable;

public class EditarTarefa extends AppCompatActivity {

    private final static String TAG = "EditarTarefaActivity";
    private Button btnSaveTask, btnDeleteTask;
    private EditText tarefa;

    DatabaseHelper databaseHelper;
    private String tarefaSelecionada;
    private int idSelecionado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        btnSaveTask = (Button) findViewById(R.id.btnSaveTask);
        btnDeleteTask = (Button) findViewById(R.id.btnDeleteTask);
        tarefa = (EditText) findViewById(R.id.tarefa);
        databaseHelper = new DatabaseHelper(this);

        Intent recieveIntent = getIntent();

        idSelecionado = recieveIntent.getIntExtra("id", -1);
        tarefaSelecionada = recieveIntent.getStringExtra("nome");

        tarefa.setText(tarefaSelecionada);
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = tarefa.getText().toString();
                if(!item.equals("")){
                    databaseHelper.updateTask(item, idSelecionado, tarefaSelecionada);
                    toast("Alteração salva com sucesso");
                }else{
                    toast("Preencha a tarefa corretamente");
                }
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

    private void toast(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
