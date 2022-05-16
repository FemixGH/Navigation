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
import android.widget.TextView;
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
    TextView testMention;
    private final String mKEY_TEXT = "myKey_text";
    private final String mKEY_PHOTO = "myKey_photo";
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String SHARED_PREFS_PHOTO = "sharedPrefs_photo";
    private static final String TEXT = "text";
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


        //SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);






        //edit.setText(pref.getString("text", null));

        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        testMention = binding.testMention;

        search = binding.button2;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                //editor.putString("text", edit.getText().toString());
                //editor.apply();

                SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(TEXT, edit.getText().toString());
                editor.apply();
                Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();

                String text = sharedPreferences.getString(TEXT, "");

                testMention.setText(text);

                //org.example.Test.testingSentimentAnalyse();


                String textSample = "Strange that I did not know him then,hat friend of mine! I did not even show him then One friendly sign";



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

                SharedPreferences sharedPreferences2 = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();

                editor2.putString("photo", data);

                editor2.apply();


                Uri mPhoto = Uri.parse(data);
                image.setImageURI(mPhoto);

            }
        });



    }

}