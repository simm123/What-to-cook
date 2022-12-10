package com.simmcard.cook;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.simmcard.cook.ui.dashboard.DishModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Utils {
    public static String getDish(View view, Context context){
        File file = new File(context.getFilesDir(), "Receipts.json");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            Snackbar error = Snackbar.make(view, (CharSequence) String.format("An error occured while reading the Receipt: %s", e), 5000);
            error.show();
            return null;
        }
    }

    public static JSONArray getDishJSONArray(Context context){
        JSONArray jsonArray;
        try {
            String responce = Utils.getDish(null, context);
            if (responce.isEmpty()){
                Toast.makeText(context, "jsjsdje", Toast.LENGTH_SHORT).show();
                return new JSONArray();
            }
            jsonArray = new JSONArray(responce);
        }
        catch (Exception e){
            Log.e("SimmMSG", e.toString());
            return null;
        }
        return jsonArray;
    }

    public static ArrayList<DishModel> getDishArrayList(Context context, JSONArray jsonArray){
        ArrayList<DishModel> dishModels = new ArrayList<>();
        try{
            for (int i = 0; i < jsonArray.length(); i++){
                dishModels.add(getDishFromJSONObject(jsonArray.getJSONObject(i)));
            }
        }
        catch (Exception e){

        }
        return dishModels;
    }

    public static DishModel getDishFromJSONObject(JSONObject jsonObject){
        DishModel dishModel;
        try{
            dishModel = new DishModel((String) jsonObject.get("name"), (JSONArray) jsonObject.get("ingredients"), (String) jsonObject.getString("description"));
            return dishModel;
        }
        catch (Exception e){
            return null;
        }
    }
}
