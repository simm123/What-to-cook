package com.simmcard.cook.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.simmcard.cook.R;
import com.simmcard.cook.Utils;
import com.simmcard.cook.databinding.FragmentDashboardBinding;
import com.simmcard.cook.ui.dashboard.adapters.DishesAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    //region creating stuff
    private FragmentDashboardBinding binding;
    private Button newDishButton;
    private RecyclerView recyclerView;
    public static ArrayList<DishModel> dishModels;
    public static DishesAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        newDishButton = (Button)binding.newDishButton;
        newDishButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                openNewDishActivity(view);
            }
        });

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView(view);
        //loadDishList();
    }

    void setupRecyclerView(@NonNull View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewDish);
        dishModels = Utils.getDishArrayList(requireContext(), Utils.getDishJSONArray(requireContext()));
        adapter = new DishesAdapter(getContext(), dishModels);
        recyclerView.setAdapter(adapter);
    }
    //endregion

    void loadDishList(){
        try {
            JSONArray jsonArray = Utils.getDishJSONArray(requireContext());
            dishModels = Utils.getDishArrayList(requireContext(), jsonArray);
        }
        catch (Exception e){
            Log.e("DEUTSCH", e.toString());
        }
        adapter.notifyItemInserted(dishModels.size() - 1);
    }

    //region adding and removing dishes
    public static void addDishToList(DishModel dishModel, AppCompatActivity appCompatActivity){
        addDishToList(dishModel.name, dishModel.ingredients, dishModel.description, appCompatActivity);
    }

    public static void addDishToList(String name, JSONArray ingredients, String description, AppCompatActivity appCompatActivity){
        //Bool to check if saving succeeded
        boolean succeed = true;

        DishModel dishModel = new DishModel(name, ingredients, description);
        try {
            JSONObject jsonObject = DishModel.createJSONObject(appCompatActivity.findViewById(R.id.edit_dish_activity), dishModel);
            dishModels.add(dishModel);
            adapter.notifyItemInserted(dishModels.size()-1);
            JSONArray jsonArray = new JSONArray();
            JSONArray loadedDishes = Utils.getDishJSONArray(appCompatActivity.getApplicationContext());
            for (int i = 0; i < loadedDishes.length(); i++){
                jsonArray.put(loadedDishes.get(0));
            }
            jsonArray.put(jsonObject);
            File file = new File(appCompatActivity.getFilesDir(), "Receipts.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonArray.toString());
            bufferedWriter.close();
        } catch (Exception e) {
            succeed = false;
            Log.e("SimmMSG", e.toString());
            appCompatActivity.finish();
        }

        if (!succeed) Toast.makeText(appCompatActivity, (CharSequence) "Gericht nicht gespeichert!", Toast.LENGTH_SHORT).show();
    }

    public static void removeDishTemporary(int position){
        dishModels.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public static void removeDishPermanent(int position, AppCompatActivity appCompatActivity){
        boolean succeed = true;
        try {
         JSONArray loadedDishes = Utils.getDishJSONArray(appCompatActivity);
         loadedDishes.remove(position);
         File file = new File(appCompatActivity.getFilesDir(), "Receipts.json");
         FileWriter fileWriter = new FileWriter(file);
         BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
         bufferedWriter.write(loadedDishes.toString());
         bufferedWriter.close();
        }
        catch (Exception e){
         appCompatActivity.finish();
        }
    }
    //endregion

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void openNewDishActivity(View view) {
        startActivity(new Intent(getContext(), EditDishActivity.class));
    }

    public static void openViewDishActivity(AppCompatActivity appCompatActivity, int position){
        Intent intent = new Intent(appCompatActivity, ViewDishActivity.class);
        DishModel dishModel = dishModels.get(position);
        intent.putExtra("name", dishModel.name);
        intent.putExtra("ingredients", dishModel.ingredients.toString());
        intent.putExtra("description", dishModel.description);
        Toast.makeText(appCompatActivity, dishModel.name, Toast.LENGTH_SHORT).show();
        appCompatActivity.startActivity(intent);
    }
}