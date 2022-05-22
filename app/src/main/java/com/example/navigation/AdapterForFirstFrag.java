package com.example.navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.databinding.FragmentFragment1Binding;

public class AdapterForFirstFrag extends RecyclerView.Adapter<AdapterForFirstFrag.MyViewHolder> {
    OnSeekBarListner onSeekBarListner;
    SeekBar seekBar_2;
    String[] list;
    ExampleOfOneFilter[] manyExamples;

    public AdapterForFirstFrag(String[] list, ExampleOfOneFilter[] manyExamples, OnSeekBarListner onSeekBarListner){
        this.list = list;
        this.manyExamples = manyExamples;
        this.onSeekBarListner = onSeekBarListner;
    }
    public AdapterForFirstFrag(String[] list, ExampleOfOneFilter[] manyExamples, MyViewHolder holder){
        this.list = list;
        this.manyExamples = manyExamples;
    }

    @NonNull
    @Override
    public AdapterForFirstFrag.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterForFirstFrag.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.
                card_view_editting_filter, parent, false), seekBar_2);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(list[position]);
        holder.example.setName(list[position]);
        holder.textValue.setText("50");
        holder.typeOfFilter = list[position];
    }




    @Override
    public int getItemCount() {
        return list.length;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        String typeOfFilter;
        ExampleOfOneFilter example = new ExampleOfOneFilter("name", 50);
        TextView textView;
        SeekBar seek;
        TextView textValue;
        public MyViewHolder(@NonNull View itemView, SeekBar seekBar_2) {
            super(itemView);
            textView = itemView.findViewById(R.id.card_view_filters_text);
            seek = itemView.findViewById(R.id.card_view_filters_seekBar);
            textValue = itemView.findViewById(R.id.card_view_filter_value);
            seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    textValue.setText(String.valueOf(i));


                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }
    }
}