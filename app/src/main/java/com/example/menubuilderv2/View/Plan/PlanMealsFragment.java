package com.example.menubuilderv2.View.Plan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menubuilderv2.Adapter.DayAdapter;
import com.example.menubuilderv2.Adapter.MealAdapter;
import com.example.menubuilderv2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PlanMealsFragment extends Fragment {

    private RecyclerView recyclerViewDays;
    private RecyclerView recyclerViewMeals;
    private DayAdapter dayAdapter;
    private MealAdapter mealAdapter;
    private List<String> daysOfWeek;
    private TextView tvCurrentWeek;
    private Calendar calendar;

    public PlanMealsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_meals, container, false);

        // Set up Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar_plan_meals);
        toolbar.setTitle("Meal Planner");

        // Initialize views and RecyclerViews
        tvCurrentWeek = view.findViewById(R.id.tv_current_week);
        Button btnPreviousWeek = view.findViewById(R.id.btn_previous_week);
        Button btnNextWeek = view.findViewById(R.id.btn_next_week);
        recyclerViewDays = view.findViewById(R.id.recyclerView_days);
        recyclerViewMeals = view.findViewById(R.id.recyclerView_meals);

        // Set up RecyclerViews
        recyclerViewDays.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(getContext(), 7));
        recyclerViewMeals.setLayoutManager(new LinearLayoutManager(getContext()));

        // Data for the days of the week
        daysOfWeek = new ArrayList<>();
        calendar = Calendar.getInstance();

        // Initialize MealAdapter
        mealAdapter = new MealAdapter();
        recyclerViewMeals.setAdapter(mealAdapter);  // Setting the meal adapter

        // Set up DayAdapter and handle onClick for each day
        dayAdapter = new DayAdapter(daysOfWeek, calendar, (position, selectedDate) -> {
            // Display the selected day in a toast
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
            String selectedDayString = dateFormat.format(selectedDate.getTime());
            Toast.makeText(getContext(), "Selected Date: " + selectedDayString, Toast.LENGTH_SHORT).show();

            // Update meals based on the selected day
            mealAdapter.updateMealsForDate(selectedDate);  // Call this to update the meals for the selected date
        });

        recyclerViewDays.setAdapter(dayAdapter);

        // Use Calendar to manage the week
        updateWeek();

        // Navigate to previous week
        btnPreviousWeek.setOnClickListener(v -> {
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
            updateWeek();
        });

        // Navigate to next week
        btnNextWeek.setOnClickListener(v -> {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            updateWeek();
        });

        return view;
    }

    private void updateWeek() {
        // Update the list of days in the week
        daysOfWeek.clear();
        Calendar tempCal = (Calendar) calendar.clone();
        tempCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.getDefault());
        for (int i = 0; i < 7; i++) {
            daysOfWeek.add(dateFormat.format(tempCal.getTime()));  // Add day to the list
            tempCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        dayAdapter.onWeekChanged();
        dayAdapter.notifyDataSetChanged();

        // Update the current week title
        SimpleDateFormat weekFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        tvCurrentWeek.setText(weekFormat.format(calendar.getTime()));
    }
}
