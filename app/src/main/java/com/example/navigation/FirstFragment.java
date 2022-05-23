package com.example.navigation;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.annotation.UiThread;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.databinding.FragmentFragment1Binding;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;




public class FirstFragment extends Fragment {
    private static final String SHARED_PREFS = "sharedPrefs_photo";
    private static final String KEY = "myKey";
    boolean isAnyTask = false;
    float lastChanges;
    long timeChanges = System.currentTimeMillis();
    Bitmap bitmap;
    Button add;
    ImageView exampleImage;
    TextView title;
    EditText title_edit;
    String[] nameOfFilters = {"Contrast","Saturation", "ColorOverlay", "Brightness", "Vignette"};
    ExampleOfOneFilter[] Examples = {new ExampleOfOneFilter("Contrast", 50),
                                    new ExampleOfOneFilter("Saturation", 50),
                                    new ExampleOfOneFilter("ColorOverlay", 50),
                                    new ExampleOfOneFilter("Contrast", 50),
                                    new ExampleOfOneFilter("Brightness", 50),
                                    new ExampleOfOneFilter("Vignette", 50)};
    //Contrast, ToneCurve, Saturation, ColorOverlay, Brightness, Vignette

    SeekBar seek_contrast, seek_saturation, seek_colorOverlay, seek_brightness, seek_vignette;
    TextView text_contrast, text_saturation, text_colorOverlay, text_brightness, text_vignette;
    TextView text_value_contrast, text_value_saturation, text_value_colorOverlay, text_value_brightness, text_value_vignette;
    float contrast=1, colorOverlay, saturation=1;
    int vignette = 0,brightness = 0;
    float last_contrast=1, last_colorOverlay, last_saturation=1;
    int last_vignette = 0,last_brightness = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentFragment1Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragment1Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.getRoot();
        return binding.getRoot();

    }

    public FirstFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }




    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(getActivity());
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bitmap = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.primer);
        title_edit = binding.titleEditOnFiltersEditor;
        add = binding.AddNewFilterButton;
        exampleImage = binding.examplePhoto;
        setFilteredBitmap(contrast,  saturation, colorOverlay, brightness, vignette);
        title_edit.setOnFocusChangeListener((view1, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            }
        });
        //tabLayout.setVisibility(View.GONE);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Filter has been added", Toast.LENGTH_SHORT).show();
            }
            //Contrast, ToneCurve, Saturation, ColorOverlay, Contrast, Brightness, Vignette
        });

        binding.constContrastSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                float x = ((float)i)*2/1000;
                binding.constContrastValue.setText(Float.toString(x));
                contrast=x;

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette);}}
        });
        binding.constSaturationSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float x = ((float)i)*2/1000;
                saturation = x;
                binding.constSaturationValue.setText(Float.toString(x));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette);}}
        });
        binding.constBrightnessSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.constBrightnessValue.setText(Integer.toString(i));
                brightness = i;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette);}
            }
        });
        binding.constVignetteSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.constVignetteValue.setText(Integer.toString(i));
                vignette=i;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette);}
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    //Contrast, ToneCurve, Saturation, ColorOverlay, Brightness, Vignette
    public void setFilteredBitmap(float contrast_1,float saturation_1, float colorOverlay_1, int brightness_1, int vignette_1){
            new Thread() {
                public void run() {
                    Filter myFilter = new Filter();
                    myFilter.addSubFilter(new ContrastSubFilter(contrast_1));
                    myFilter.addSubFilter(new BrightnessSubFilter(brightness_1));
                    myFilter.addSubFilter(new SaturationSubFilter(saturation_1));
                    myFilter.addSubFilter(new VignetteSubFilter(getContext(), vignette_1));

                    Bitmap image = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap bit = myFilter.processFilter(image);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            exampleImage.setImageBitmap(bit);
                            isAnyTask=false;
                        }
                    });

                }
            }.start();


    }
    public boolean shouldChange(float contrast,float saturation,
            int vignette,int brightness){
        if(isAnyTask==false || modulFloat(last_contrast, contrast)>0.03
                                                        || modulFloat(last_saturation, saturation)>0.03
                                                        || modulInt(last_brightness,brightness)>3
                                                        || modulInt(last_vignette,vignette)>10){
            last_brightness=brightness;
            last_contrast=contrast;
            last_saturation=saturation;
            last_vignette=vignette;
            return true;
        }else {
            return false;
        }
    }
    public float modulFloat(float a, float b){
        if(a>=b){
            return a-b;
        }else {
            return b-a;
        }
    }
    public int modulInt(int a, int b){
        if(a>=b){
            return a-b;
        }else {
            return b-a;
        }
    }
    public String saveToInternalFilteredStorage(Bitmap bitmapImage, String name) {
        //сохраняет с заданым именем в папку с сохранёнными отфильтроваными фотографиями. НУЖНО писать .jpg
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, name);
        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to
            // the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public Bitmap loadImageFilteredFromStorage(String  name) {
        //достаёт по пути фотку из отфильтрованных
        Bitmap b = null;
        try {
            ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
            File path1 = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f = new File(path1, name);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }
    public String saveToInternalStorage(Bitmap bitmapImage) {
        //сохраняет фотку, которая поставится в главный экран при перезапуске активности
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "photo.jpg");
        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to
            // the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage() {
        // остает последнюю фотку, это та, которая в главном экране стоит
        Bitmap b = null;
        try {
            ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
            File path1 = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f = new File(path1, "photo.jpg");
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }
}