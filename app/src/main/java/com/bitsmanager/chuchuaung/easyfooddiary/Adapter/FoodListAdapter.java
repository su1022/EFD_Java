package com.bitsmanager.chuchuaung.easyfooddiary.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitsmanager.chuchuaung.easyfooddiary.Activity.FoodItemActivity;
import com.bitsmanager.chuchuaung.easyfooddiary.Interface.FoodViewClickListener;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.Food;
import com.bitsmanager.chuchuaung.easyfooddiary.R;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> implements Filterable {

    List<Food> foodList;
    List<Food> originalList;
    Context context;
    private FoodViewClickListener listener;

    public void setListener(FoodViewClickListener listener) {
        this.listener = listener;
    }

    public FoodListAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
        originalList = new ArrayList<>(foodList);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.food_item_view, viewGroup, false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(v);
        foodViewHolder.setFoodViewClickListener(listener);
        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int i) {
        foodViewHolder.bindData(foodList.get(i));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    @Override
    public Filter getFilter() {
        return foodFilter;
    }

    private Filter foodFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Food> filterList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(originalList);
            } else {
                String filterptn = constraint.toString().toLowerCase().trim();

                for (Food item : originalList) {
                    if (item.getName().toLowerCase().contains(filterptn)) {
                        filterList.add(item);
                    }
                }
            }
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            foodList.clear();
            foodList.addAll((List<Food>) results.values);
            notifyDataSetChanged();
        }
    };




    public class FoodViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_foodItem;
        CardView cardFood;
        TextView tv_item_foodName, tv_item_foodCalorie;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            cardFood = (CardView) itemView.findViewById(R.id.cardFood);
            layout_foodItem = (LinearLayout) itemView.findViewById(R.id.layout_foodItem);
            tv_item_foodName = (TextView) itemView.findViewById(R.id.tv_item_foodName);
            tv_item_foodCalorie = (TextView) itemView.findViewById(R.id.tv_item_foodCalorie);
        }

        public void bindData(Food food) {
            food.getFood_type_id();
            tv_item_foodName.setText(""+food.getName());
            tv_item_foodCalorie.setText(""+food.getCalorie()+" Cal");
        }

        public void setFoodViewClickListener(final FoodViewClickListener listener) {
            if (listener != null) {
                layout_foodItem.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        listener.onClick(foodList.get(getAdapterPosition()));
                        return false;
                    }
                });

                layout_foodItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.showCustomDialog(foodList.get(getAdapterPosition()));
                        ;
                    }
                });
                layout_foodItem.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        listener.onEdit(foodList.get(getAdapterPosition()),getAdapterPosition());
                        return false;

                    }
                });

            }
        }


    }
}
