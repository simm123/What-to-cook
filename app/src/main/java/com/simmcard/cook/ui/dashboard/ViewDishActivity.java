package com.simmcard.cook.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.simmcard.cook.R;
import com.simmcard.cook.ui.dashboard.adapters.IngredientsAdapter;
import com.simmcard.cook.ui.dashboard.adapters.IngredientsRecyclerViewAdapter;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewDishActivity extends AppCompatActivity {
    String name;
    String description;
    TextView nameTextView;
    TextView descriptionTextView;
    IngredientsRecyclerViewAdapter ingredientsAdapter;
    JSONArray ingredientsJSONArray;
    ArrayList<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dish);
        Bundle extras = getIntent().getExtras();
        nameTextView = findViewById(R.id.dish_name_activity_view_dish);
        descriptionTextView = findViewById(R.id.dish_description_activity_view_dish);
        if (extras != null){
            name = (String) extras.get("name");
            description = (String) extras.get("description");
            try {
                ingredientsJSONArray = new JSONArray((String) extras.get("ingredients"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        nameTextView.setText(name);
        descriptionTextView.setText(description);
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_activity_view_dish);
        ingredients = new ArrayList<>();
        try {
            for (int i = 0; i < ingredientsJSONArray.length(); i++){
                Ingredient ingredient = new Ingredient(ingredientsJSONArray.getString(i));
                ingredients.add(ingredient);
            }
        }
        catch (Exception e){
            Log.e("SimmMSG", e.toString());
            finish();
        }
        ingredientsAdapter = new IngredientsRecyclerViewAdapter(getBaseContext(), ingredients);
        recyclerView.setAdapter(ingredientsAdapter);
    }
}