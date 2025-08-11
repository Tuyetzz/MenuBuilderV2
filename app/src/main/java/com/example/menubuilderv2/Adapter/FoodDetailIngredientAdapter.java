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
import com.example.menubuilderv2.Model.UsedIngredients;
import com.example.menubuilderv2.R;

import java.util.List;

public class FoodDetailIngredientAdapter extends RecyclerView.Adapter<FoodDetailIngredientAdapter.ViewHolder> {

    private final Context context;
    private final List<UsedIngredients> usedIngredients;

    public FoodDetailIngredientAdapter(Context context, List<UsedIngredients> usedIngredients) {
        this.context = context;
        this.usedIngredients = usedIngredients;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_detail_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsedIngredients used = usedIngredients.get(position);
        if (used.getIngredient() != null) {
            holder.txtName.setText(used.getIngredient().getName());
            holder.txtCategory.setText(used.getIngredient().getCategory());
            Glide.with(context)
                    .load(used.getIngredient().getImage())
                    .placeholder(R.drawable.ic_lunch)
                    .into(holder.img);
        } else {
            holder.txtName.setText("");
            holder.txtCategory.setText("");
            holder.img.setImageResource(R.drawable.ic_lunch);
        }
        holder.txtQuantity.setText(used.getQuantity());
    }

    @Override
    public int getItemCount() {
        return usedIngredients != null ? usedIngredients.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        UsedIngredients used = usedIngredients.get(position);
        String key = used != null && used.getIngredient() != null ? used.getIngredient().getId() : ("pos_" + position);
        return key.hashCode();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtName;
        TextView txtCategory;
        TextView txtQuantity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgIngredient);
            txtName = itemView.findViewById(R.id.txtIngredientName);
            txtCategory = itemView.findViewById(R.id.txtIngredientCategory);
            txtQuantity = itemView.findViewById(R.id.txtQuantityIngredient);
        }
    }
}


