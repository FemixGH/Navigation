package com.example.navigation;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
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

import androidx.activity.result.ActivityResultCallback;
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
    private final String mKEY_TEXT = "myKey_text";
    private final String mKEY_PHOTO = "myKey_photo";
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String SHARED_PREFS_PHOTO = "sharedPrefs_photo";
    public  ImageView image;
    public Uri mPhoto;


    ActivityResultLauncher<String> mTakePhoto;

    public void setImage_2(Uri uri){
        this.image.setImageURI(uri);
    }

    private FragmentFragment2Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragment2Binding.inflate(inflater, container, false);


        return binding.getRoot();
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit = binding.SearchTextOn2;
        image = binding.capturedImageSecond;
        edit = view.findViewById(R.id.Search_text_on_2);
        image = view.findViewById(R.id.capturedImageSecond);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();




        edit.setText(pref.getString("text", null));

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
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("text", edit.getText().toString());
                editor.apply();
            }
        });


        gallery = binding.gallery;
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTakePhoto.launch("image/*");

            }
        });

        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        image.setImageURI(result);

                    }
                }


        );

        getParentFragmentManager().setFragmentResultListener("dataFrom1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                String data = result.getString("df1");
                editor.putString("photo", data);
                editor.apply();
                Uri mPhoto = Uri.parse(data);
                image.setImageURI(mPhoto);

            }
        });



    }

}