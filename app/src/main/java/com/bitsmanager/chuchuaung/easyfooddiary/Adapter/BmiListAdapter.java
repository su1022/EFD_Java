package com.bitsmanager.chuchuaung.easyfooddiary.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bitsmanager.chuchuaung.easyfooddiary.DAO.BmiSQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.DAO.UserSQLDao;
import com.bitsmanager.chuchuaung.easyfooddiary.Interface.BmiViewClickListener;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.BMI;
import com.bitsmanager.chuchuaung.easyfooddiary.Model.User;
import com.bitsmanager.chuchuaung.easyfooddiary.R;
import com.bitsmanager.chuchuaung.easyfooddiary.Utility.Utility;

import java.util.List;

public class BmiListAdapter extends RecyclerView.Adapter<BmiListAdapter.BmiViewHolder>{

    List<BMI> bmiList;
    Context context;
    private BmiViewClickListener listener;
    private UserSQLDao userSQLDao;

    public void setListener(BmiViewClickListener listener) {
        this.listener = listener;
    }

    public BmiListAdapter(List<BMI> bmiList, Context context){
        this.bmiList = bmiList;
        this.context = context;
        userSQLDao = new UserSQLDao(context);
    }

    @NonNull
    @Override
    public BmiListAdapter.BmiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_bmi_view, viewGroup, false);
        BmiViewHolder bmiViewHolder = new BmiViewHolder(view);
        bmiViewHolder.setViewClickListener(listener);
        return bmiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BmiListAdapter.BmiViewHolder bmiViewHolder, int i) {
        bmiViewHolder.bindData(bmiList.get(i));
    }

    @Override
    public int getItemCount() {
        return bmiList.size();
    }

    public class BmiViewHolder extends RecyclerView.ViewHolder {

        CardView cardBMIView;
        TextView tvWeight, tvHeight, tvResult;
        ImageButton imgBtnDelete;
       int is_Kg = 1;
       int is_cm = 1;
       int user_Id = 1;

        public BmiViewHolder(@NonNull View itemView) {
            super(itemView);
            cardBMIView = (CardView) itemView.findViewById(R.id.cardBMIView);
            imgBtnDelete = (ImageButton) itemView.findViewById(R.id.imgBtnDelete);
            tvWeight = (TextView) itemView.findViewById(R.id.tvWeight);
            tvHeight = (TextView) itemView.findViewById(R.id.tvHeight);
            tvResult = (TextView) itemView.findViewById(R.id.tvResult);
        }

        public void bindData(BMI bmi){
            User user = userSQLDao.getUser(bmi.getUser_id());
            if (bmi.getIs_Kg() == is_Kg){
                tvWeight.setText("Weight : " +(int)bmi.getBmi_weight() +" kg");
            }else{
                tvWeight.setText("Weight : " +(int)bmi.getBmi_weight() +" lb");
            }
            if (bmi.getIs_cm() == is_cm){
                tvHeight.setText("Height : " +(int)bmi.getBmi_height() + " cm");
            }else {
                tvHeight.setText("Height : " +(int)bmi.getBmi_height()/12 + " ft" + "  "+ (int)bmi.getBmi_height()%12 + "in");
            }
            tvResult.setText("Result : " +bmi.getResult());

        }

        public void setViewClickListener(final BmiViewClickListener listener){
            if(listener != null){
                imgBtnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onDeleteClick(bmiList.get(getAdapterPosition()));
                    }
                });
            }
        }
    }

}
