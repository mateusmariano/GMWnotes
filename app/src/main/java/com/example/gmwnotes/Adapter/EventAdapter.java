package com.example.gmwnotes.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmwnotes.Database.EntityClass;
import com.example.gmwnotes.R;

import org.w3c.dom.Text;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    Context context;
    List<EntityClass> entityClasses;

    public EventAdapter(Context context, List<EntityClass> entityClasses){
        this.context = context;
        this.entityClasses = entityClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView eventText, timeAndDateText;
        private LinearLayout topLayout;
        public ViewHolder(@NonNull View itemView) {
           super(itemView);

        }
    }
}
