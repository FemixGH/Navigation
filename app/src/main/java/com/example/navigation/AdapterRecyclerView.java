package com.example.navigation;

import static androidx.camera.core.CameraX.getContext;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.databinding.FragmentFragment2Binding;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

import java.util.ArrayList;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder> {


    String[] list;

    ArrayList<Bitmap> photos;
    ArrayList<FullFilter> filters;

    public AdapterRecyclerView(ArrayList<FullFilter> filters,ArrayList<Bitmap> photos){
        this.filters = filters;
        this.photos = photos;
    }

    @NonNull
    @Override
    public AdapterRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerView.MyViewHolder holder, int position) {
        holder.textView.setText(filters.get(position).getNameFilter());
        holder.imageView.setImageBitmap(photos.get(position));
    }




    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private AdapterRecyclerView adapterRecyclerView;

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
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.action_popup_delete:
                            adapterRecyclerView.photos.remove(getAbsoluteAdapterPosition());
                            adapterRecyclerView.filters.remove(getAbsoluteAdapterPosition());
                            adapterRecyclerView.notifyItemRemoved(getAbsoluteAdapterPosition());
                            return true;
                            case R.id.action_popup_info:
                                FullFilter f = adapterRecyclerView.filters.get(getAbsoluteAdapterPosition());
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                Bundle argument = new Bundle();
                                argument.putSerializable("key",f);
                                on_second_fragment nextFrag= new on_second_fragment(f);
                                nextFrag.setArguments(argument);
                                String backStateName = nextFrag.getClass().getName();
                                activity.getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.openNewFragment, nextFrag)

                                        .addToBackStack(backStateName)
                                        .commit();


                                return true;
                        default:
                            return false;
                    }

                }
            });
            popupMenu.show();

        }
        public  MyViewHolder linkAdapter(AdapterRecyclerView adapter){
            this.adapterRecyclerView=adapter;
            return this;
        }

    }


}