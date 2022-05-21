package com.example.navigation;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.navigation.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;

import bogdan.imagefilters.effect.bitmap.SimpleBitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Properties;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final String SHARED_PREFS = "sharedPrefs";

    private static final int PERMISSION_REQUEST_CAMERA = 87678;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 56755;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 234123;

    private ActivityMainBinding binding;

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==PERMISSION_REQUEST_CAMERA && grantResults.length>0
//        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//
//        }
//    }
    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode());
        destroyPref();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        tabLayout = binding.tableLayout;
        viewPager = binding.viewpager;

        tabLayout.setupWithViewPager(viewPager);
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and sentiment
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new SecondFragment(), "EDITING");
        vpAdapter.addFragment(new ThirdFragment(), "GALLERY OF Filters");
        vpAdapter.addFragment(new FirstFragment(), "CUSTOM FILTER");


        viewPager.setAdapter(vpAdapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
        }


        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(30));
        myFilter.addSubFilter(new ContrastSubFilter(1.1f));
        Bitmap bitmapPhoto = BitmapFactory.decodeResource(getResources(), R.mipmap.kit);
        Bitmap image = bitmapPhoto.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap outputImage = myFilter.processFilter(image);

        // Assume block needs to be inside a Try/Catch block.
        String path = Environment.getExternalStorageDirectory().toString();

        File file = new File(path, "LOLOLO.jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.


        //outputImage.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate


        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    private void destroyPref() {
        SharedPreferences preferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("text", "").commit();
        Log.d("MY_TAG", "cleared");
    }


}
