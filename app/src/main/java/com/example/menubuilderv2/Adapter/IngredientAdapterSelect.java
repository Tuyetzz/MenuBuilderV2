package com.example.menubuilderv2.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.menubuilderv2.Model.Ingredient;
import com.example.menubuilderv2.Model.UsedIngredients;
import com.example.menubuilderv2.R;

import java.util.List;
import java.util.Map;

public class IngredientAdapterSelect extends RecyclerView.Adapter<IngredientAdapterSelect.IngredientSelectViewHolder> {

    private final Context context;
    private List<Ingredient> ingredientList;
    private final OnIngredientSelectedListener listener;
    private final Map<String, UsedIngredients> selectedMap;

    public interface OnIngredientSelectedListener {
        void onIngredientSelected(Ingredient ingredient, String quantity);
    }

    public IngredientAdapterSelect(Context context, List<Ingredient> ingredientList,
                                   OnIngredientSelectedListener listener,
                                   Map<String, UsedIngredients> selectedMap) {
        this.context = context;
        this.ingredientList = ingredientList;
        this.listener = listener;
        this.selectedMap = selectedMap;
    }

    @NonNull
    @Override
    public IngredientSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient_select, parent, false);
        return new IngredientSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientSelectViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);

        holder.txtIngredientName.setText(ingredient.getName());
        holder.txtIngredientCategory.setText(ingredient.getCategory());
        Glide.with(context)
                .load(ingredient.getImage())
                .placeholder(R.drawable.ic_lunch)
                .into(holder.imgIngredient);

        // Xoá TextWatcher cũ (nếu có)
        if (holder.quantityWatcher != null) {
            holder.edtQuantity.removeTextChangedListener(holder.quantityWatcher);
        }

        UsedIngredients selected = selectedMap.get(ingredient.getId());
        if (selected != null) {
            holder.checkSelect.setChecked(true);
            holder.edtQuantity.setVisibility(View.VISIBLE);
            holder.edtQuantity.setText(selected.getQuantity());
        } else {
            holder.checkSelect.setChecked(false);
            holder.edtQuantity.setVisibility(View.GONE);
            holder.edtQuantity.setText("");
        }

        holder.checkSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.edtQuantity.setVisibility(View.VISIBLE);
                if (holder.edtQuantity.getText().toString().isEmpty()) {
                    holder.edtQuantity.setText("1");
                }
                listener.onIngredientSelected(ingredient, holder.edtQuantity.getText().toString());
            } else {
                holder.edtQuantity.setVisibility(View.GONE);
                listener.onIngredientSelected(ingredient, "");
            }
        });

        holder.quantityWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.checkSelect.isChecked()) {
                    listener.onIngredientSelected(ingredient, s.toString());
                }
            }
        };

        holder.edtQuantity.addTextChangedListener(holder.quantityWatcher);
    }

    @Override
    public int getItemCount() {
        return ingredientList != null ? ingredientList.size() : 0;
    }

    public static class IngredientSelectViewHolder extends RecyclerView.ViewHolder {
        TextView txtIngredientName, txtIngredientCategory;
        CheckBox checkSelect;
        EditText edtQuantity;
        ImageView imgIngredient;
        TextWatcher quantityWatcher; // Thêm để quản lý TextWatcher an toàn

        public IngredientSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIngredientName = itemView.findViewById(R.id.txtIngredientName);
            txtIngredientCategory = itemView.findViewById(R.id.txtIngredientCategory);
            checkSelect = itemView.findViewById(R.id.checkSelect);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);
            imgIngredient = itemView.findViewById(R.id.imgIngredient);
        }
    }

    public void updateList(List<Ingredient> newList) {
        if (newList != null) {
            ingredientList = newList;
            notifyDataSetChanged();
        }
    }
}
