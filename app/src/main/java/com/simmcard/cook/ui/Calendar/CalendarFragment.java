package com.simmcard.cook.ui.Calendar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.simmcard.cook.R;
import com.simmcard.cook.databinding.FragmentCalendarBinding;
import com.simmcard.cook.ui.dashboard.DishModel;
import com.simmcard.cook.ui.dashboard.adapters.DishesAdapter;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment {
    private Button openSelectDishButton;
    private FragmentCalendarBinding binding;
    private TextView dishNameTextView;
    private Date selectedDate;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        openSelectDishButton = binding.buttonOpenSelectDishMenu;
        openSelectDishButton.setOnClickListener(view -> {
            openSelectDishMenu();
        });
        final CalendarView calendarView = binding.calendarView;
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(i, i1, i2);
                //long time = (long) ((i - 1970) * 31556952000L) + ((i1 - 1) * 2629800000L) + (i2 * 604800000L);
                updateDishCardView(new Date(calendar.getTimeInMillis()));
            }
        });
        return root;
    }

    void openSelectDishMenu(){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.recycler_view_dish);
        dialog.show();
        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view_layout_show_dish);
        ArrayList<DishModel> dishModels = new ArrayList<DishModel>();
        dishModels.add(new DishModel("Some", new JSONArray(), "somee"));
        DishesAdapter dishesAdapter = new DishesAdapter(getContext(), dishModels);
        dishesAdapter.setOnItemClickListeners(view -> {
            dialog.dismiss();

            //TODO pass real dishmodel
            CalendarSystem.saveDish(new DishModel("sjskld", new JSONArray(), "spmee"), selectedDate, getContext());
            Toast.makeText(getContext(), "YESS", Toast.LENGTH_SHORT).show();
        }, null);
        recyclerView.setAdapter(dishesAdapter);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dishNameTextView = view.findViewById(R.id.dish_name_fragment_calendar);
        updateDishCardView(Calendar.getInstance().getTime());
    }

    void updateDishCardView(Date date){
        dishNameTextView.setText(CalendarSystem.getDish(date).name);
        String file = CalendarSystem.getFile(getContext());
        //dishNameTextView.setText(CalendarSystem.getHashMap(file, getContext()).toString());
        selectedDate = date;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}