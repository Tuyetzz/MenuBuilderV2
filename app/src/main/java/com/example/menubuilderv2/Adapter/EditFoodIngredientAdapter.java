package com.example.menubuilderv2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.menubuilderv2.Model.UsedIngredients;
import com.example.menubuilderv2.R;

import java.util.List;

public class EditFoodIngredientAdapter extends RecyclerView.Adapter<EditFoodIngredientAdapter.ViewHolder> {

    private final Context context;
    private final List<UsedIngredients> usedIngredients;
    private final OnIngredientActionListener listener;

    public interface OnIngredientActionListener {
        void onEditIngredient(UsedIngredients usedIngredient, int position);
        void onDeleteIngredient(int position);
    }

    public EditFoodIngredientAdapter(Context context, List<UsedIngredients> usedIngredients, OnIngredientActionListener listener) {
        this.context = context;
        this.usedIngredients = usedIngredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_food_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsedIngredients used = usedIngredients.get(position);
        if (used.getIngredient() != null) {
            holder.txtName.setText(used.getIngredient().getName());
            holder.txtCategory.setText(used.getIngredient().getCategory());
            holder.txtQuantity.setText(used.getQuantity());
            
            Glide.with(context)
                    .load(used.getIngredient().getImage())
                    .placeholder(R.drawable.ic_lunch)
                    .into(holder.imgIngredient);
        }

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditIngredient(used, position);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteIngredient(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usedIngredients != null ? usedIngredients.size() : 0;
    }

    public void updateList(List<UsedIngredients> newList) {
        usedIngredients.clear();
        usedIngredients.addAll(newList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIngredient;
        TextView txtName, txtCategory, txtQuantity;
        ImageButton btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIngredient = itemView.findViewById(R.id.imgIngredient);
            txtName = itemView.findViewById(R.id.txtIngredientName);
            txtCategory = itemView.findViewById(R.id.txtIngredientCategory);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            btnEdit = itemView.findViewById(R.id.btnEditIngredient);
            btnDelete = itemView.findViewById(R.id.btnDeleteIngredient);
        }
    }
}
