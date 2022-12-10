package com.simmcard.cook.ui.dashboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.simmcard.cook.R;
import com.simmcard.cook.ui.dashboard.Ingredient;

import java.util.ArrayList;

public class IngredientsAdapter extends ArrayAdapter<Ingredient> {
    /*int o = 0;
    ArrayList<Ingredient> ingredients;*/
    public IngredientsAdapter(Context context, ArrayList<Ingredient> ingredients){
        super(context, 0, ingredients);
        //this.ingredients = ingredients;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Ingredient ingredient = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_edit_ingredient, parent, false);

            //Toast.makeText(getContext(), (CharSequence)ingredients.get(o).name, Toast.LENGTH_SHORT).show();
            //o++;
        }
        return convertView;
    }
}