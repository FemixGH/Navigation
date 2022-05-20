package com.example.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.databinding.CardViewBinding;
import com.example.navigation.databinding.FragmentFragment3Binding;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder> {


    String[] list;

    public AdapterRecyclerView(String[] list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.
                card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(list[position]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.infoText);
        }
    }
}
