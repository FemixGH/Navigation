package com.example.navigation;

import android.content.SharedPreferences;
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

import com.example.navigation.databinding.FragmentFragment2Binding;
import com.example.navigation.databinding.FragmentOnSecondFragmentBinding;

import java.io.Serializable;


public class on_second_fragment extends Fragment {
    FullFilter filter;

    Button back;
    FragmentOnSecondFragmentBinding binding;

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        Serializable s = savedInstanceState.getSerializable("key");
        super.onCreate(savedInstanceState);

//        if(s!=null) {
//            filter = (FullFilter) getActivity().getIntent().getSerializableExtra("key");
//        }
    }

    public on_second_fragment() {
    }

    public on_second_fragment(FullFilter filter) {
        this.filter = filter;
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
        binding.infoNameText.setText(filter.getNameFilter());
        binding.infoBrightnessValue.setText(Integer.toString(filter.getBrightness()));
        binding.infoColorCorrectionAlphaValue.setText(Integer.toString(filter.getColorOverlay_depth()));
        binding.infoContrastValue.setText(Float.toString(filter.getContrast()));
        binding.infoSaturationValue.setText(Float.toString(filter.getSaturation()));
        binding.infoVignetteValue.setText(Integer.toString(filter.getVignette()));
        binding.infoColorCorrectionRedValue.setText(Float.toString(filter.getColorOverlay_red()));
        binding.infoColorCorrectionGreenValue.setText(Float.toString(filter.getColorOverlay_green()));
        binding.infoColorCorrectionBlueValue.setText(Float.toString(filter.getColorOverlay_blue()));


    }
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.onSecondFragment,fragment);
        fragmentTransaction.commit();

    }
}