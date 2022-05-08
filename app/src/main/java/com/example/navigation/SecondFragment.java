package com.example.navigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.navigation.databinding.FragmentFragment2Binding;


public class SecondFragment extends Fragment {
    Button search,gallery;
    EditText edit;
    private final String KEY = "myKey";
    private static final String SHARED_PREFS = "sharedPrefs";
    public  ImageView image;

    ActivityResultLauncher<String> mTakePhoto;

    public void setImage_2(Uri uri){
        this.image.setImageURI(uri);
    }

    private FragmentFragment2Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragment2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit = binding.SearchTextOn2;
        image = binding.capturedImageSecond;

        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        search = binding.button2;
        search.setOnClickListener(view1 -> Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show());

        gallery = binding.gallery;
        gallery.setOnClickListener(view12 -> mTakePhoto.launch("image/*"));

        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> image.setImageURI(result)

        );

        getParentFragmentManager().setFragmentResultListener("dataFrom1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                String data = result.getString("df1");
                Uri mPhoto = Uri.parse(data);
                image.setImageURI(mPhoto);

            }
        });


    }
}