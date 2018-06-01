package com.example.dawid.projectpum.DAL.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.dawid.projectpum.DAL.CheckboxesEnums;
import com.example.dawid.projectpum.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Dawid on 23.04.2018.
 */

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.ViewHolder> {

    public List<CheckboxesEnums.Diet> enumValues;
    public ArrayList<Boolean> IsSelected;
    private Context context;

    public DietAdapter(ArrayList<Boolean> isSelected) {
        enumValues = new ArrayList<CheckboxesEnums.Diet>(EnumSet.allOf(CheckboxesEnums.Diet.class));
        IsSelected = isSelected;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkbox_item,null);
        context = parent.getContext();
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setCheckBox(enumValues.get(position).getName());
        if(IsSelected != null && IsSelected.size() != 0)
        {
            holder.check(IsSelected.get(position));
        }
        else {
            IsSelected = new ArrayList<Boolean>(Arrays.asList(new Boolean[getItemCount()]));
            Collections.fill(IsSelected, false);
        }
    }

    @Override
    public int getItemCount() {
        return enumValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        private void setCheckBox(String text){this.checkBox.setText(text);}
        private void check(Boolean bool){this.checkBox.setChecked(bool);}

        @OnClick(R.id.checkbox) void doSth(){
            IsSelected.set(getAdapterPosition(),this.checkBox.isChecked());
        }

        @BindView(R.id.checkbox)
        CheckBox checkBox;
    }
}
