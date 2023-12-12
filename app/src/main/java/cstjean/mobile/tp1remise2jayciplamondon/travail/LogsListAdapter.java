package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cstjean.mobile.tp1remise2jayciplamondon.R;

class LogsListAdapter extends RecyclerView.Adapter<LogsViewHolder> {
    private ArrayList<String> logsList;

    public LogsListAdapter(ArrayList<String> logsList) {
        this.logsList = logsList;
    }

    @NonNull
    @Override
    public LogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_deplacement_item, parent, false);
        return new LogsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogsViewHolder holder, int position) {
        holder.bindCoursSession(logsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return logsList.size();
    }
}

class LogsViewHolder extends RecyclerView.ViewHolder {
    private final TextView deplacement;

    LogsViewHolder(@NonNull View itemView) {
        super(itemView);
        deplacement = itemView.findViewById(R.id.item_txt_deplacement);
    }

    void bindCoursSession(String log, int position) {
        deplacement.setText(position + 1 + ". " + log);
    }
}