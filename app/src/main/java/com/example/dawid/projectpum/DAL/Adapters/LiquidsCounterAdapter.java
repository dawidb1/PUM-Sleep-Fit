package com.example.dawid.projectpum.DAL.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dawid.projectpum.DAL.CheckboxesEnums;
import com.example.dawid.projectpum.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 23.04.2018.
 */

public class LiquidsCounterAdapter extends RecyclerView.Adapter<LiquidsCounterAdapter.ViewHolder> {

    public List<CheckboxesEnums.Liquids> enumValues;
    private Context context;

    public LiquidsCounterAdapter() {
        enumValues = new ArrayList<CheckboxesEnums.Liquids>(EnumSet.allOf(CheckboxesEnums.Liquids.class));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.liquids_item,null);
        context = parent.getContext();
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(enumValues.get(position).getName());
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
        private void setText(String text){this.textView.setText(text);}

        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.numberPicker)
        NumberPicker numberPicker;
    }
}
