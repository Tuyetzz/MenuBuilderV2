package com.example.menubuilderv2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.menubuilderv2.Model.Ingredient;
import com.example.menubuilderv2.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapterManage extends RecyclerView.Adapter<IngredientAdapterManage.IngredientViewHolder> {

    private final Context context;
    private final List<Ingredient> ingredientList;
    private final OnIngredientActionListener listener;

    public interface OnIngredientActionListener {
        void onEdit(Ingredient ingredient);
        void onDelete(Ingredient ingredient);
    }

    public IngredientAdapterManage(Context context, List<Ingredient> ingredientList, OnIngredientActionListener listener) {
        this.context = context;
        this.ingredientList = new ArrayList<>(ingredientList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.txtName.setText(ingredient.getName());
        holder.txtCategory.setText(ingredient.getCategory());

        Glide.with(context)
                .load(ingredient.getImage())
                .placeholder(R.drawable.ic_lunch)
                .into(holder.imgIngredient);

        holder.itemView.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_ingredient_item, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_edit) {
                    listener.onEdit(ingredient);
                    return true;
                } else if (item.getItemId() == R.id.action_delete) {
                    listener.onDelete(ingredient);
                    return true;
                }
                return false;
            });

            popup.show();
        });

    }


    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIngredient;
        TextView txtName, txtCategory;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIngredient = itemView.findViewById(R.id.imgIngredient);
            txtName = itemView.findViewById(R.id.txtName);
            txtCategory = itemView.findViewById(R.id.txtCategory);
        }
    }

    public void updateList(List<Ingredient> newList) {
        ingredientList.clear();
        ingredientList.addAll(newList);
        notifyDataSetChanged();
    }
}
