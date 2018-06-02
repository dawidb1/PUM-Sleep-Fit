package com.example.dawid.projectpum.DAL.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dawid.projectpum.DAL.CheckboxesEnums;
import com.example.dawid.projectpum.DAL.PhysicalItemVM;
import com.example.dawid.projectpum.R;

import java.io.Console;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 15.05.2018.
 */

public class PhysicalAdapter extends RecyclerView.Adapter<PhysicalAdapter.ViewHolder>{
    private Context context;
    public ArrayList<PhysicalItemVM> PhysicalList;

    public PhysicalAdapter(ArrayList<PhysicalItemVM> List) {
        PhysicalList = List;
    }

    @Override
    public PhysicalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.physical_item,null);
        context = parent.getContext();
        return new PhysicalAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(PhysicalAdapter.ViewHolder holder, int position) {
        holder.setSportName(PhysicalList.get(position).getSportName());
        holder.setSportValue(PhysicalList.get(position).getSportValue());
        holder.setSportTime(PhysicalList.get(position).getSportTime());

        int modulo = position%3;
        Log.i("cokolwiek",modulo + "");
        holder.setItemColor(modulo);
    }

    @Override
    public int getItemCount() {
        return PhysicalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        private void setSportName(String text){this.sportName.setText(text);}
        private void setSportValue(String text){this.sportValue.setText(text);}
        private void setSportTime(String text){this.sportTime.setText(text);}
        private void setItemColor(int modulo){
            int color;
            switch (modulo){
                case 0:color = ContextCompat.getColor(context,R.color.secondaryColor);
                break;
                case 1: {
                    color = ContextCompat.getColor(context,R.color.secondaryDarkColor);
//                    this.sportName.setTextColor(ContextCompat.getColor(context,R.color.secondaryTextColor));
//                    this.sportValue.setTextColor(ContextCompat.getColor(context,R.color.secondaryTextColor));
//                    this.sportTime.setTextColor(ContextCompat.getColor(context,R.color.secondaryTextColor));
                    break;
                }
                case 2: color = ContextCompat.getColor(context,R.color.secondaryLightColor);
                break;
                default: color = ContextCompat.getColor(context,R.color.secondaryColor);
                break;
            }
            this.itemLayout.setBackgroundColor(color);
        }

        @BindView(R.id.item_layout)
        LinearLayout itemLayout;
        @BindView(R.id.sport_name)
        TextView sportName;
        @BindView(R.id.sport_value)
        TextView sportValue;
        @BindView(R.id.sport_time)
        TextView sportTime;
    }
}
