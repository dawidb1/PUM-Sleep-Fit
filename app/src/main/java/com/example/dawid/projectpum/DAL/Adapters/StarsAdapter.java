package com.example.dawid.projectpum.DAL.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Created by Dawid on 16.05.2018.
 */

public class StarsAdapter extends RecyclerView.Adapter<StarsAdapter.ViewHolder> {
//    public List<CheckboxesEnums.Liquids> enumValues;
    public boolean[] Stars;
    private Context context;

    public StarsAdapter(int size) {
//        enumValues = new ArrayList<CheckboxesEnums.Liquids>(EnumSet.allOf(CheckboxesEnums.Liquids.class));
        Stars = new boolean[5];
        for (int i = 0; i<size; i++){
            Stars[i] = true;
        }
    }

    @Override
    public StarsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.star_item,null);
        context = parent.getContext();
        return new StarsAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(StarsAdapter.ViewHolder holder, int position) {
//        holder.setText(enumValues.get(position).getName());
        holder.setDrawable(Stars[position]);
    }

    @Override
    public int getItemCount() {
//        return enumValues.size();
        return Stars.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        private void setDrawable(boolean hasStar){
//            this.textView.setText(text);
            if (hasStar){
                this.starButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
            }
            else {
                this.starButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
            }
        }

        @BindView(R.id.star_button)
        Button starButton;
    }


}
