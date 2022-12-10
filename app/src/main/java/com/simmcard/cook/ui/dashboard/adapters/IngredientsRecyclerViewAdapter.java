package com.simmcard.cook.ui.dashboard.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simmcard.cook.R;
import com.simmcard.cook.ui.dashboard.Ingredient;

import java.util.ArrayList;

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientsRecyclerViewHolder> {
    Context context;
    ArrayList<Ingredient> ingredients;

    public IngredientsRecyclerViewAdapter(Context context, ArrayList<Ingredient> ingredients){
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_ingredient, parent, false);
        return new IngredientsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsRecyclerViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(ingredient.name);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientsRecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public IngredientsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_show_ingredient_name);
        }
    }
}
