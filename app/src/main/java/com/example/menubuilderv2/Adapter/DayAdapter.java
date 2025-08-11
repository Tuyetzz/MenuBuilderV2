package com.example.menubuilderv2.Adapter;

import android.graphics.Typeface;
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
    private int selectedPosition = -1;
    private boolean weekHasToday = false;
    private int todayPosition = -1;

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
        // Fixed item size handled by item_day.xml to avoid clipping

        // Clone the calendar object for each day and set click listener
        Calendar selectedDate = (Calendar) currentWeekStart.clone();
        selectedDate.add(Calendar.DAY_OF_WEEK, position);

        // Style: selected bold, today border if different from selected (no font size change)
        boolean isSelected = position == selectedPosition;
        boolean isToday = weekHasToday && position == todayPosition;

        // Reset default styles
        holder.dayText.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        holder.dayText.setTextColor(holder.itemView.getResources().getColor(android.R.color.black));
        holder.itemView.setBackground(null);

        if (isSelected) {
            holder.dayText.setTypeface(Typeface.DEFAULT_BOLD);
            holder.itemView.setBackground(holder.itemView.getResources().getDrawable(R.drawable.day_selected_bg));
            holder.dayText.setTextColor(holder.itemView.getResources().getColor(android.R.color.white));
        } else if (isToday) {
            holder.itemView.setBackground(holder.itemView.getResources().getDrawable(R.drawable.day_today_border));
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            if (listener != null) {
                listener.onDayClick(position, selectedDate);
            }
        });
    }


    @Override
    public int getItemCount() {
        return days.size();
    }

    public void onWeekChanged() {
        // Compute whether today is within the current week and its position
        Calendar startOfWeek = (Calendar) currentWeekStart.clone();
        startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        Calendar endOfWeek = (Calendar) startOfWeek.clone();
        endOfWeek.add(Calendar.DAY_OF_MONTH, 6);

        Calendar today = Calendar.getInstance();
        weekHasToday = !today.before(startOfWeek) && !today.after(endOfWeek);

        if (weekHasToday) {
            long diffMs = today.getTimeInMillis() - startOfWeek.getTimeInMillis();
            todayPosition = (int) (diffMs / (24L * 60L * 60L * 1000L));
            if (selectedPosition < 0 || selectedPosition > 6) {
                selectedPosition = todayPosition;
            }
        } else {
            todayPosition = -1;
            if (selectedPosition < 0 || selectedPosition > 6) {
                selectedPosition = 0;
            }
        }
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayText;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.tv_day);
        }
    }
}
