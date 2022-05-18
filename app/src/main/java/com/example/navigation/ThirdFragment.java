package com.example.navigation;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.databinding.FragmentFragment3Binding;

import java.util.ArrayList;
import java.util.Date;


public class ThirdFragment extends Fragment {

    ArrayList<String> filterComments = new ArrayList<String>();
    RecyclerView recyclerView;

    //RecyclerView recyclerView2;

    ArrayList images, name;

    private FragmentFragment3Binding binding;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragment3Binding.inflate(inflater, container, false);
        filterComments.add("filter1");
        filterComments.add("filter1");

        recyclerView = binding.recycleView;
        RVAdapter rvAdapter = new RVAdapter(getContext(),filterComments);
        recyclerView.setAdapter(new RVAdapter(getContext(),filterComments));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        View view = binding.getRoot();
        filterComments.add("filter1");
        filterComments.add("filter1");
//todo
//        recyclerView = binding.recycleView;
//        recyclerView2 = binding.recycleView;
//        images = new ArrayList();
//        name = new ArrayList();
//
//        for (int i = 0; i <Data.names.lenght; i++){
//            images.add(Data.images);
//            name.add(Date.names);
//        }


        recyclerView.setAdapter(new RVAdapter(getContext(),filterComments));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        return inflater.inflate(R.layout.fragment_fragment3, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}