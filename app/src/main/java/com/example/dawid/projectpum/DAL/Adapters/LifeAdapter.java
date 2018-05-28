package com.example.dawid.projectpum.DAL.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.dawid.projectpum.DAL.CheckboxesEnums;
import com.example.dawid.projectpum.R;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 23.04.2018.
 */

public class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.ViewHolder> {

    public List<CheckboxesEnums.LifeStyle> enumValues;
    private Context context;

    public LifeAdapter() {
        enumValues = new ArrayList<CheckboxesEnums.LifeStyle>(EnumSet.allOf(CheckboxesEnums.LifeStyle.class));
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

        @BindView(R.id.checkbox)
        CheckBox checkBox;
    }
}