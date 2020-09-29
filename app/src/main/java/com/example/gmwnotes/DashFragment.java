package com.example.gmwnotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

public class DashFragment extends Fragment {

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

}
