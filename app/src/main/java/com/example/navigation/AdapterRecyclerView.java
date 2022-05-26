package com.example.navigation;

import static androidx.camera.core.CameraX.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;
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
    Activity a;

    ArrayList<Bitmap> photos;
    ArrayList<FullFilter> filters;
    SharedPreferences prefs;

    public AdapterRecyclerView(ArrayList<FullFilter> filters, ArrayList<Bitmap> photos, SharedPreferences prefs, Activity a){
        this.filters = filters;
        this.photos = photos;
        this.prefs=prefs;
        this.a=a;
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
                @SuppressLint("RestrictedApi")
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.action_popup_apply:

                            FullFilter f = adapterRecyclerView.filters.get(getAbsoluteAdapterPosition());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("df3", f);

                            AppCompatActivity activityA = (AppCompatActivity) view.getContext();
                            FragmentManager manager = activityA.getSupportFragmentManager();
                            manager.setFragmentResult("dataFrom3", bundle);

                            return true;
                        case R.id.action_popup_delete:

                            if(getAbsoluteAdapterPosition()>=4) {
                                adapterRecyclerView.photos.remove(getAbsoluteAdapterPosition());
                                adapterRecyclerView.filters.remove(getAbsoluteAdapterPosition());
                                adapterRecyclerView.notifyItemRemoved(getAbsoluteAdapterPosition());
                                @SuppressLint("RestrictedApi") SharedPreferences sharedPreferences = getContext().getSharedPreferences("filter_names_2", Context.MODE_PRIVATE);
                                int n = sharedPreferences.getInt("number_of_filters", 1);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                int i = getAbsoluteAdapterPosition();
                                String key = sharedPreferences.getString(Integer.toString(getAbsoluteAdapterPosition()), "");
                                for (int t = i + 1; i < n; i++) {
                                    String s = sharedPreferences.getString(Integer.toString(t), "");
                                    sharedPreferences.edit().putString(Integer.toString(t - 1), s);
                                }
                                @SuppressLint("RestrictedApi") SharedPreferences pref = getContext().getSharedPreferences(key, Context.MODE_PRIVATE);
                                editor.remove(Integer.toString(getAbsoluteAdapterPosition())).commit();
                                SharedPreferences.Editor mEditor = pref.edit();
                                editor.apply();
                                mEditor.clear().commit();
                                n -= 1;
                                sharedPreferences.edit().putInt("number_of_filters", n).commit();

                            }else{
                                Toast.makeText(getContext(),"You can't delete default filter", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                            case R.id.action_popup_info:
                                FullFilter fi = adapterRecyclerView.filters.get(getAbsoluteAdapterPosition());
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                Bundle argument = new Bundle();
                                argument.putSerializable("key",fi);
                                on_second_fragment nextFrag= new on_second_fragment(fi);
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