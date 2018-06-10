package com.example.dawid.projectpum.DAL.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawid.projectpum.DAL.CheckboxesEnums;
import com.example.dawid.projectpum.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Dawid on 23.04.2018.
 */

public class LiquidsCounterAdapter extends RecyclerView.Adapter<LiquidsCounterAdapter.ViewHolder> {

    public List<CheckboxesEnums.Liquids> enumValues;
    public ArrayList<Integer> numberPickersState;

    private Context context;

    public LiquidsCounterAdapter(ArrayList<Integer> npStates) {
        enumValues = new ArrayList<CheckboxesEnums.Liquids>(EnumSet.allOf(CheckboxesEnums.Liquids.class));
        numberPickersState = npStates;
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
        numberPickersState.add(0);
        holder.setListener();
    }

    @Override
    public int getItemCount() {
        return enumValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements NumberPicker.OnValueChangeListener{
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        private void setText(String text){this.textView.setText(text);}
        private void setListener(){
            numberPicker.setOnValueChangedListener(this);
        }

        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.numberPicker)
        NumberPicker numberPicker;

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            numberPickersState.set(getAdapterPosition(),newVal);
        }
    }
}
