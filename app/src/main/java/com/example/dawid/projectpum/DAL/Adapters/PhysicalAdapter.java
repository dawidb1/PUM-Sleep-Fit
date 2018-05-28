package com.example.dawid.projectpum.DAL.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dawid.projectpum.DAL.CheckboxesEnums;
import com.example.dawid.projectpum.DAL.PhysicalItemVM;
import com.example.dawid.projectpum.R;

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

    public PhysicalAdapter() {
        PhysicalList = new ArrayList<>();
        PhysicalItemVM item = new PhysicalItemVM("Piłka nożna","duża","40");
        PhysicalList.add(item);
        PhysicalList.add(item);
        PhysicalList.add(item);
        PhysicalList.add(item);
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

        @BindView(R.id.sport_name)
        TextView sportName;
        @BindView(R.id.sport_value)
        TextView sportValue;
        @BindView(R.id.sport_time)
        TextView sportTime;
    }
}
