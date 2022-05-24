package com.example.navigation;

import static androidx.camera.core.CameraX.getContext;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

import java.util.ArrayList;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder> {


    String[] list;
    ArrayList<String> filters;
    ArrayList<Bitmap> photos;

    public AdapterRecyclerView(ArrayList<String> filters,ArrayList<Bitmap> photos){
        this.filters = filters;
        this.photos = photos;
    }

    @NonNull
    @Override
    public AdapterRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterRecyclerView.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.
                card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerView.MyViewHolder holder, int position) {
        holder.textView.setText(filters.get(position));
        holder.imageView.setImageBitmap(photos.get(position));
    }




    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        Button menu;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.infoText);
            menu = itemView.findViewById(R.id.card_menu);
            imageView = itemView.findViewById(R.id.imageViewRecycler);
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowPopupMenu(view);
                }
            });
        }
        private void ShowPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.show();

        }

    }


}