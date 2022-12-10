package com.simmcard.cook.ui.dashboard;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class DishModel implements Serializable {
    public String name = "This is an example receipt/dish";
    public JSONArray ingredients;
    public String description;

    public DishModel(String name, JSONArray ingredients, String description){
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
    }

    public static JSONObject createJSONObject(View view, String name, JSONArray ingredients, String description){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("ingredients", ingredients);
            jsonObject.put("description", description);
        }
        catch (Exception e){
            Snackbar error = Snackbar.make(view, (CharSequence) String.format("An error occured while creating the Receipt: %s", e), 5000);
            error.show();
        }

        //jsonObject.put(new DashboardFragment().getContext(), (CharSequence)"Ingredients");
        return jsonObject;
    }

    public static JSONObject createJSONObject(View view, DishModel dishModel){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", dishModel.name);
            jsonObject.put("ingredients", dishModel.ingredients);
            jsonObject.put("description", dishModel.description);
        }
        catch (Exception e){
            Snackbar error = Snackbar.make(view, (CharSequence) String.format("An error occured while creating the Receipt: %s", e), 5000);
            error.show();
        }

        //jsonObject.put(new DashboardFragment().getContext(), (CharSequence)"Ingredients");
        return jsonObject;
    }

    public DishModel load(){
        return this;
    }

}
