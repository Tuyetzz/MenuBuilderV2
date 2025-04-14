package com.example.menubuilderv2.Adapter;

import android.content.Context;
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
import com.example.menubuilderv2.R;

import java.util.List;

public class IngredientAdapterSelect extends RecyclerView.Adapter<IngredientAdapterSelect.IngredientSelectViewHolder> {

    private final Context context;
    private List<Ingredient> ingredientList;
    private OnIngredientSelectedListener listener;

    public interface OnIngredientSelectedListener {
        void onIngredientSelected(Ingredient ingredient, String quantity);
    }

    public IngredientAdapterSelect(Context context, List<Ingredient> ingredientList, OnIngredientSelectedListener listener) {
        this.context = context;
        this.ingredientList = ingredientList;
        this.listener = listener;
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
        holder.checkSelect.setChecked(false);
        holder.edtQuantity.setVisibility(View.GONE);

        // checkbox
        holder.checkSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show quantity input field if checkbox is checked
                holder.edtQuantity.setVisibility(View.VISIBLE);

                // If quantity is empty, set it to 1
                if (holder.edtQuantity.getText().toString().isEmpty()) {
                    holder.edtQuantity.setText("1");
                }


                listener.onIngredientSelected(ingredient, holder.edtQuantity.getText().toString());
            } else {
                // Hide quantity input if checkbox is unchecked
                holder.edtQuantity.setVisibility(View.GONE);
                listener.onIngredientSelected(ingredient, "");  // Clear quantity when unchecked
            }
        });

        // TextWatcher to update listener when quantity is modified
        holder.edtQuantity.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (holder.checkSelect.isChecked()) {
                    String quantity = holder.edtQuantity.getText().toString();
                    listener.onIngredientSelected(ingredient, quantity);
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });
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
