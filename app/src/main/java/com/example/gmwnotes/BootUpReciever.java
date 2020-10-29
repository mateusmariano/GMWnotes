package com.example.gmwnotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootUpReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,DashFragment.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
