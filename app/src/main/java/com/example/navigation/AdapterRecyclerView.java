package com.example.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.databinding.CardViewBinding;
import com.example.navigation.databinding.FragmentFragment3Binding;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterRecyclerView extends RecyclerView.Adapter {

    Context context;
    ArrayList arrayList, arrayListName;


    public AdapterRecyclerView(Context context, ArrayList arrayList, ArrayList arrayListName) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListName = arrayListName;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        viewHolderClass.imageView.setImageResource((Integer) arrayList.get(position));
        viewHolderClass.textView.setText(arrayListName.get(position).toString());

        viewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolderClass extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewRecycler);
            textView = (TextView) itemView.findViewById(R.id.infoText);
        }
    }
}
