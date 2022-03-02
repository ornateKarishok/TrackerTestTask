package com.example.trackertesttask.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackertesttask.R;
import com.example.trackertesttask.model.WorkSession;
import com.example.trackertesttask.util.DataStorage;

import java.text.SimpleDateFormat;
import java.util.List;

public class WorkSessionAdapter extends RecyclerView.Adapter<WorkSessionAdapter.ViewHolder> {
    private final List<WorkSession> workSessions;
    private final OnDeleteSessionClickListener onDeleteSessionClickListener;

    public WorkSessionAdapter(List<WorkSession> workSessions, OnDeleteSessionClickListener onDeleteSessionClickListener) {
        this.workSessions = workSessions;
        this.onDeleteSessionClickListener = onDeleteSessionClickListener;
    }

    @NonNull
    @Override
    public WorkSessionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(WorkSessionAdapter.ViewHolder holder, int position) {
        WorkSession session = workSessions.get(position);
        Double durationOfWork = session.getDuration();
        holder.quantityHours.setText(String.valueOf(durationOfWork));
        double sumPerHour = Double.parseDouble(DataStorage.getDataFromStorage(holder.getContext(), MainActivity.SUM_PER_HOUR));
        holder.sum.setText(new StringBuilder().append("( ").append(durationOfWork * sumPerHour).toString());
        holder.currency.setText(new StringBuilder().append(DataStorage.getDataFromStorage(holder.getContext(), MainActivity.CURRENCY_ID)).append(" )").toString());
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss ");
        holder.date.setText(formatter.format(session.getDate()));
        holder.cancelButton.setOnClickListener(v ->
                holder.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return workSessions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView quantityHours, date, sum, currency;
        private final ImageView cancelButton;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            quantityHours = view.findViewById(R.id.quantity_hours);
            date = view.findViewById(R.id.date);
            sum = view.findViewById(R.id.sum);
            currency = view.findViewById(R.id.currency);
            cancelButton = view.findViewById(R.id.cancel_button);
        }

        public Context getContext() {
            return view.getContext();
        }

        private void onItemClick(int position) {
            onDeleteSessionClickListener.onButtonClick(position);
        }
    }

    public interface OnDeleteSessionClickListener {
        void onButtonClick(int position);
    }
}
