package com.simmcard.cook.ui.dashboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.simmcard.cook.R;
import com.simmcard.cook.ui.dashboard.DashboardFragment;
import com.simmcard.cook.ui.dashboard.DishModel;

import java.util.ArrayList;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.DishViewHolder>{
    private Context context;
    private ArrayList<DishModel> dishModels;
    //ViewArray views = new ViewArray(new ArrayList<>());
    public View.OnClickListener onItemClickListener;
    public View.OnLongClickListener onItemLongClickListener;

    public DishesAdapter(Context context, ArrayList<DishModel> dishModels){
        this.context = context;
        this.dishModels = dishModels;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_dish, parent, false);
        return new DishViewHolder(view, onItemClickListener, onItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        DishModel dishModel = dishModels.get(position);
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(dishModel.name);
    }

    public void setOnItemClickListeners(View.OnClickListener onItemClickListener, View.OnLongClickListener onItemLongClickListener){
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public int getItemCount() {
        return dishModels.size();
    }

    public void removeDishModel(int position){
        dishModels.remove(position);
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public View view;
        public DishViewHolder(@NonNull View itemView, View.OnClickListener onItemClickListener, View.OnLongClickListener onItemLongClickListener){
            super(itemView);
            view = itemView;
            nameTextView = (TextView) itemView.findViewById(R.id.item_dish_button);
            /*itemView.setOnClickListener(view -> {
                DashboardFragment.openViewDishActivity((AppCompatActivity) itemView.getContext(), getBindingAdapterPosition());
            });
            itemView.setOnLongClickListener(view -> {
                int position = getBindingAdapterPosition();
                AppCompatActivity appCompatActivity = (AppCompatActivity) itemView.getContext();
                DashboardFragment.removeDishTemporary(position);
                DashboardFragment.removeDishPermanent(position, appCompatActivity);
                return true;
            });*/
            itemView.setOnClickListener(onItemClickListener);
            itemView.setOnLongClickListener(onItemLongClickListener);
        }
    }
}