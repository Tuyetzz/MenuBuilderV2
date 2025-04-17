package com.example.menubuilderv2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.menubuilderv2.Model.Food;
import com.example.menubuilderv2.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private List<Food> foodList;
    private OnFoodClickListener listener;

    public interface OnFoodClickListener {
        void onFoodClick(Food food);
    }

    public FoodAdapter(Context context, List<Food> foodList, OnFoodClickListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.txtName.setText(food.getName());
        holder.txtDesc.setText(food.getDesc());
        holder.txtCategory.setText(food.getCategory());

        // Load image
//        Glide.with(context)
//                .load(food.getImage())
//                .placeholder(R.drawable.ic_lunch)
//                .into(holder.imgFood);

        // Click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFoodClick(food);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName, txtDesc, txtCategory;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imageFood);
            txtName = itemView.findViewById(R.id.txtFoodName);
            txtDesc = itemView.findViewById(R.id.txtFoodDesc);
            txtCategory = itemView.findViewById(R.id.txtFoodCategory);
        }
    }
}
