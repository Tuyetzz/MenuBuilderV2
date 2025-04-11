package com.example.menubuilderv2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubuilderv2.R;

import java.util.Calendar;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private List<String> days;
    private OnDayClickListener listener;
    private Calendar currentWeekStart;

    public interface OnDayClickListener {
        void onDayClick(int position, Calendar selectedDate);
    }

    public DayAdapter(List<String> days, Calendar currentWeekStart, OnDayClickListener listener) {
        this.days = days;
        this.currentWeekStart = currentWeekStart;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        String day = days.get(position);
        holder.dayText.setText(day);

        // Get screen width and set the item width to 1/7th of the screen width
        int screenWidth = holder.itemView.getContext().getResources().getDisplayMetrics().widthPixels;
        int itemWidth = screenWidth / 7; // divide by 7 for the 7 days of the week

        // Set the width of the item
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = itemWidth;
        holder.itemView.setLayoutParams(layoutParams);

        // Clone the calendar object for each day and set click listener
        Calendar selectedDate = (Calendar) currentWeekStart.clone();
        selectedDate.add(Calendar.DAY_OF_WEEK, position);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDayClick(position, selectedDate);
            }
        });
    }


    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayText;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.tv_day);
        }
    }
}
