package com.simmcard.cook.ui.Calendar;

import android.content.Context;
import android.widget.Toast;

import com.simmcard.cook.ui.dashboard.DishModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.NotImplementedError;

public class CalendarSystem {
    public static DishModel getDish(Date date){
        return new DishModel(date.toString(), new JSONArray(), "SOMEEE");
    }

    public static JSONArray getJSONArray(){
        throw new NotImplementedError();
    }

    public static void saveDish(DishModel dishModel, Date date, Context context){
        try {
            //create hashmap to save
            HashMap<Date, DishModel> dishModels = new HashMap<>();

            //put dishModel into hashmap
            dishModels.put(date, dishModel);

            //create JSONObject of hashmap
            JSONObject objectToSave = new JSONObject();
            objectToSave.put("list", dishModels);
            //store it in a file
            File file = new File(context.getFilesDir(), "Calendar.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(objectToSave.toString());
            bufferedWriter.close();
        }
        catch (Exception e){
            Toast.makeText(context, "Failed: " + e.toString(), Toast.LENGTH_LONG).show();
            throw new RuntimeException("calendar failed");
        }
    }

    public static String getFile(Context context){
        String result = null;
        try {
            File file = new File(context.getFilesDir(), "Calendar.json");
            if (file.exists()){
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null){
                    stringBuilder.append(line).append("\n");
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                result = stringBuilder.toString();
            }
            else {
                return "Please select a dish!";
            }
        }
        catch (Exception e){
            Toast.makeText(context, "Failed: " + e.toString(), Toast.LENGTH_LONG).show();
            throw new RuntimeException("calendar failed");
        }

        return result;
    }

    public static HashMap<Date, DishModel> getHashMap(String file, Context context){
        HashMap<Date, DishModel> hashMap = null;
        try{
            JSONObject jsonObject = new JSONObject(file);
            hashMap = (HashMap<Date, DishModel>) jsonObject.get("list");
        }
        catch (Exception e){
            Toast.makeText(context, "Failed: " + e.toString(), Toast.LENGTH_LONG).show();
            throw new RuntimeException("calendar failed");
        }
        return hashMap;
    }
}
