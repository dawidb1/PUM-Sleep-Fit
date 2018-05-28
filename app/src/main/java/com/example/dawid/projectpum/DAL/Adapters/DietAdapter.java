package com.example.dawid.projectpum.DAL.Adapters;

import android.content.Context;
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
import java.util.EnumSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dawid on 23.04.2018.
 */

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.ViewHolder> {

    public List<CheckboxesEnums.Diet> enumValues;
    public ArrayList<String> SelectedItems;
    private Context context;

    public DietAdapter() {
        enumValues = new ArrayList<CheckboxesEnums.Diet>(EnumSet.allOf(CheckboxesEnums.Diet.class));
        SelectedItems = new ArrayList<>();
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

        @OnClick(R.id.checkbox) void doSth(){
            SelectedItems.add(this.checkBox.getText().toString());
        }

        @BindView(R.id.checkbox)
        CheckBox checkBox;
    }
}
