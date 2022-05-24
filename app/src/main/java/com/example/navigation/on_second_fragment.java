package com.example.navigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.navigation.databinding.FragmentOnSecondFragmentBinding;


public class on_second_fragment extends Fragment {

    Button back;
    FragmentOnSecondFragmentBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     binding = FragmentOnSecondFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();



    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        back = binding.backToFragment;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ThirdFragment());
                Toast.makeText(getActivity(),  "checked", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.onSecondFragment,fragment);
        fragmentTransaction.commit();

    }
}