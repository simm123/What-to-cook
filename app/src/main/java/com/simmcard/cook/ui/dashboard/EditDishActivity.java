package com.simmcard.cook.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simmcard.cook.R;
import com.simmcard.cook.Utils;
import com.simmcard.cook.ui.dashboard.adapters.IngredientsAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class EditDishActivity extends AppCompatActivity {

    ArrayList<Ingredient> list = new ArrayList<>();
    IngredientsAdapter adapter;
    LinearLayout listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);
        setupIngredientsList();
    }

    private void setupIngredientsList(){
        listView = findViewById(R.id.ingredient_list);
        adapter = new IngredientsAdapter(this, list);
        for (int i = 0; i < 2; i++){
            addDish(null);
        }
    }

    public void addDish(View view){
        Ingredient ingredient = new Ingredient("Ingredient1");
        list.add(ingredient);
        adapter.add(ingredient);
        View item = adapter.getView(adapter.getCount()-1, null, null);
        item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LinearLayout parent = (LinearLayout) view.getParent();
                int j = parent.indexOfChild(view);
                list.remove(j);
                listView.removeView(view);
                return true;
            }
        });
        listView.addView(item);
    }

    public void saveDish(View view) {
        //Get name of Receipt
        EditText editName = (EditText)findViewById(R.id.dishName);
        String name = editName.getText().toString();

        //Get description
        EditText descriptionET = (EditText)findViewById(R.id.description);
        String description = descriptionET.getText().toString();

        //Get ingredients of Receipt
        JSONArray ingredients = new JSONArray(getIngredients());


        //Current dishes
        String currentDishes = getDish(view);

        DashboardFragment.addDishToList(name, ingredients, description, this);
    }

    List<String> getIngredients(){
        LinearLayout listView = findViewById(R.id.ingredient_list);
        List<String> ingredientsArray = new ArrayList<>();
        for (int i = 0; i < listView.getChildCount(); i++){
            EditText editText = (EditText) listView.getChildAt(i);
            ingredientsArray.add(editText.getText().toString());
        }
        Toast.makeText(getApplicationContext(), (CharSequence) ingredientsArray.toString(), Toast.LENGTH_LONG).show();
        return ingredientsArray;
    }

    public void loadDish(View view){
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(getDish(view));
    }

    public String getDish(View view){
        return Utils.getDish(view, getApplicationContext());
    }


}