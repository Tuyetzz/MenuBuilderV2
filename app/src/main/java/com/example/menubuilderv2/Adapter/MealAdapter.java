package com.example.menubuilderv2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubuilderv2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    // Placeholder for Meal data (You can add more dynamic data as per requirement)
    private Calendar selectedDate = Calendar.getInstance(); // Initialize with the current date

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        // Set button actions
        holder.btnBreakfast.setOnClickListener(v -> {
            if (selectedDate != null) {
                Toast.makeText(v.getContext(), "Breakfast on " + getFormattedDate(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Date not selected!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnLunch.setOnClickListener(v -> {
            if (selectedDate != null) {
                Toast.makeText(v.getContext(), "Lunch on " + getFormattedDate(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Date not selected!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDinner.setOnClickListener(v -> {
            if (selectedDate != null) {
                Toast.makeText(v.getContext(), "Dinner on " + getFormattedDate(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Date not selected!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        // You could dynamically decide the number of items if needed
        return 1; // Only 1 item showing the 3 meal buttons
    }

    // This method will be called when a new day is selected to update the meals based on the selected date
    public void updateMealsForDate(Calendar date) {
        this.selectedDate = date;
        notifyDataSetChanged();
    }

    private String getFormattedDate() {
        // SimpleDateFormat to show the selected date (You can modify this format as needed)
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
        return selectedDate != null ? format.format(selectedDate.getTime()) : "Unknown Date";
    }

    // ViewHolder for MealAdapter
    static class MealViewHolder extends RecyclerView.ViewHolder {
        Button btnBreakfast, btnLunch, btnDinner;

        MealViewHolder(@NonNull View itemView) {
            super(itemView);
            btnBreakfast = itemView.findViewById(R.id.btn_breakfast);
            btnLunch = itemView.findViewById(R.id.btn_lunch);
            btnDinner = itemView.findViewById(R.id.btn_dinner);
        }
    }
}
