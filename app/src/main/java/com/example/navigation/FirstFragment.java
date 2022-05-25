package com.example.navigation;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.navigation.databinding.FragmentFragment1Binding;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.concurrent.Executor;



@SuppressLint("SetTextI18n")
public class FirstFragment extends Fragment {
    FullFilter mFilterForExample = new FullFilter(getActivity(), "filter_on_example",
            "shared_preferences_for_example",
            1, 1, 0,0);
    private static final String SHARED_PREFS = "sharedPrefs_photo";
    private static final String SHARED_FILTERS = "sharedPrefs_filtrs";
    private static final String KEY = "myKey";
    boolean isAnyTask = false;
    SharedPreferences prefs = null;
    float lastChanges;
    long timeChanges = System.currentTimeMillis();
    Bitmap bitmap;
    Button add;
    ImageView exampleImage;
    TextView title;
    EditText title_edit;
    String[] nameOfFilters = {"Contrast","Saturation", "ColorOverlay", "Brightness", "Vignette"};

    //Contrast, ToneCurve, Saturation, ColorOverlay, Brightness, Vignette

    SeekBar seek_contrast, seek_saturation, seek_colorOverlay_alpha, seek_brightness, seek_vignette, seek_colorOverlay_red, seek_colorOverlay_green,seek_colorOverlay_blue;
    TextView text_contrast, text_saturation, text_colorOverlay, text_brightness, text_vignette;
    TextView text_value_contrast, text_value_saturation, text_value_colorOverlay, text_value_brightness, text_value_vignette;
    float contrast=1, colorOverlay, saturation=1;
    int vignette = 0,brightness = 0, colorOverlay_alpha;
    float last_contrast=1, last_colorOverlay, last_saturation=1,colorOverlay_red, colorOverlay_green, colorOverlay_blue;
    int last_vignette = 0,last_brightness = 0;
    ExampleOfOneFilter[] Examples = {new ExampleOfOneFilter("Contrast", contrast),
            new ExampleOfOneFilter("Saturation", saturation),
            new ExampleOfOneFilter("ColorOverlay", colorOverlay),
            new ExampleOfOneFilter("Brightness", brightness),
            new ExampleOfOneFilter("Vignette", vignette)};
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
        exampleImage = binding.examplePhoto;

        SharedPreferences shared = requireActivity().getSharedPreferences("shared_preferences_for_example", MODE_PRIVATE);
        mFilterForExample.getFilter(shared, "shared_preferences_for_example");
        mFilterForExample.getFilter(shared, "shared_preferences_for_example");
        binding.titleEditOnFiltersEditor.setText(prefs.getString("name_of_example_filter_1", "Enter filter's name"));
        contrast = mFilterForExample.getContrast();
        brightness = mFilterForExample.getBrightness();
        saturation = mFilterForExample.getSaturation();
        vignette = mFilterForExample.getVignette();
        colorOverlay_alpha = mFilterForExample.colorOverlay_depth;
        colorOverlay_red = mFilterForExample.colorOverlay_red;
        colorOverlay_green = mFilterForExample.colorOverlay_green;
        colorOverlay_blue = mFilterForExample.colorOverlay_blue;
        bitmap = BitmapFactory.decodeResource(requireContext().getResources(),
                R.drawable.primer);

        setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette,
                mFilterForExample.getColorOverlay_depth(), mFilterForExample.getColorOverlay_red(), mFilterForExample.getColorOverlay_green(),
                mFilterForExample.colorOverlay_blue);


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
        return ContextCompat.getMainExecutor(requireActivity());
    }


    public FirstFragment(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences shared = requireActivity().getSharedPreferences("shared_preferences_for_example", MODE_PRIVATE);

        mFilterForExample.getFilter(shared, "shared_preferences_for_example");

        contrast = mFilterForExample.getContrast();
        brightness = mFilterForExample.getBrightness();
        saturation = mFilterForExample.getSaturation();
        vignette = mFilterForExample.getVignette();
        colorOverlay_alpha = mFilterForExample.colorOverlay_depth;
        colorOverlay_red = mFilterForExample.colorOverlay_red;
        colorOverlay_green = mFilterForExample.colorOverlay_green;
        colorOverlay_blue = mFilterForExample.colorOverlay_blue;


        bitmap = BitmapFactory.decodeResource(requireContext().getResources(),
                R.drawable.primer);

        seek_contrast = binding.constContrastSeek;
        seek_brightness = binding.constBrightnessSeek;
        seek_saturation = binding.constSaturationSeek;
        seek_vignette = binding.constVignetteSeek;

        seek_colorOverlay_alpha = binding.constColorOverlayAlphaSeek;
        seek_colorOverlay_green = binding.constColorOverlayGreenSeek;
        seek_colorOverlay_red = binding.constColorOverlayRedSeek;
        seek_colorOverlay_blue = binding.constColorOverlayBlueSeek;

        seek_contrast.setProgress((int)(contrast*500));
        seek_saturation.setProgress((int)(saturation*500));
        seek_vignette.setProgress(vignette);
        seek_brightness.setProgress(brightness);
        seek_colorOverlay_blue.setProgress((int)(colorOverlay_blue*1000));
        seek_colorOverlay_green.setProgress((int)(colorOverlay_green*1000));
        seek_colorOverlay_red.setProgress((int)(colorOverlay_red*1000));
        seek_colorOverlay_alpha.setProgress(colorOverlay_alpha);

        binding.constBrightnessValue.setText(Integer.toString(brightness));
        binding.constVignetteValue.setText(Integer.toString(vignette));
        binding.constSaturationValue.setText(Float.toString(saturation));
        binding.constContrastValue.setText(Float.toString(contrast));

        binding.constColorOverlayAlphaValue.setText(Integer.toString(colorOverlay_alpha));
        binding.constColorOverlayRedValue.setText(Float.toString(colorOverlay_red));
        binding.constColorOverlayGreenValue.setText(Float.toString(colorOverlay_green));
        binding.constColorOverlayBlueValue.setText(Float.toString(colorOverlay_blue));

        title_edit = binding.titleEditOnFiltersEditor;
        add = binding.AddNewFilterButton;
        exampleImage = binding.examplePhoto;
        setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette,
                mFilterForExample.getColorOverlay_depth(), mFilterForExample.getColorOverlay_red(), mFilterForExample.getColorOverlay_green(),
                mFilterForExample.colorOverlay_blue);
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
                if(binding.titleEditOnFiltersEditor.getText().toString()!=null &&
                        binding.titleEditOnFiltersEditor.getText().toString().length()!=0) {
                    mFilterForExample.setNameFilter(title_edit.getText().toString());


                    SharedPreferences.Editor editor = prefs.edit();
                    int n = prefs.getInt("number_of_filters", 0);
                    boolean x = false;
                    for(int i=0;i<n;i++){

                        String name = prefs.getString(Integer.toString(i), null);

                        if(Objects.equals(name, mFilterForExample.getNameFilter())){
                            x=true;
                            Toast.makeText(getActivity(), "Filter with this name already exist, choose another one", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(x==false) {
                        mFilterForExample.setMY_PREFS_NAME(Integer.toString(n));
                        editor.putInt("number_of_filters", n+1).commit();
                        editor.putString(Integer.toString(n), mFilterForExample.getNameFilter()).commit();

                        Toast.makeText(getActivity(),mFilterForExample.getNameFilter() , Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("df1", mFilterForExample);
                        getParentFragmentManager().setFragmentResult("dataFrom1", bundle);

                        mFilterForExample.saveFilter(prefs,getActivity(),prefs);
                        Toast.makeText(getActivity(), "Filter have been successfully added", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(getActivity(), "Please, give the filter a name", Toast.LENGTH_SHORT).show();
                }
            }
            //Contrast, ToneCurve, Saturation, ColorOverlay, Contrast, Brightness, Vignette
        });

        binding.constContrastSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                float x = ((float)i)*2/1000;
                binding.constContrastValue.setText(Float.toString(x));
                contrast=x;
                Examples[0].setValueOfFilter(x);
                mFilterForExample.setContrast(x);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette,
                    mFilterForExample.getColorOverlay_depth(), mFilterForExample.getColorOverlay_red(), mFilterForExample.getColorOverlay_green(),
                    mFilterForExample.colorOverlay_blue);}}
        });
        binding.constSaturationSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float x = ((float)i)*2/1000;
                saturation = x;
                binding.constSaturationValue.setText(Float.toString(x));
                Examples[1].setValueOfFilter(x);
                mFilterForExample.setSaturation(x);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette,
                    mFilterForExample.getColorOverlay_depth(), mFilterForExample.getColorOverlay_red(), mFilterForExample.getColorOverlay_green(),
                    mFilterForExample.colorOverlay_blue);}}
        });
        binding.constBrightnessSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.constBrightnessValue.setText(Integer.toString(i));
                brightness = i;
                Examples[2].setValueOfFilter(i);
                mFilterForExample.setBrightness(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette,
                        mFilterForExample.getColorOverlay_depth(), mFilterForExample.getColorOverlay_red(), mFilterForExample.getColorOverlay_green(),
                        mFilterForExample.colorOverlay_blue);}            }
        });
        binding.constVignetteSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.constVignetteValue.setText(Integer.toString(i));
                vignette=i;
                Examples[3].setValueOfFilter(i);
                mFilterForExample.setVignette(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette,
                        mFilterForExample.getColorOverlay_depth(), mFilterForExample.getColorOverlay_red(), mFilterForExample.getColorOverlay_green(),
                        mFilterForExample.colorOverlay_blue);}            }
        });

        binding.constColorOverlayAlphaSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                colorOverlay_alpha = i;
                mFilterForExample.setColorOverlay_depth(i);
                colorOverlay_alpha = i;
                binding.constColorOverlayAlphaValue.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette,
                        mFilterForExample.getColorOverlay_depth(), mFilterForExample.getColorOverlay_red(), mFilterForExample.getColorOverlay_green(),
                        mFilterForExample.colorOverlay_blue);}
            }
        });
        binding.constColorOverlayRedSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float x = ((float)i)/1000;
                mFilterForExample.setColorOverlay_red(x);
                colorOverlay_red = x;
                binding.constColorOverlayRedValue.setText(Float.toString(x));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette,
                        mFilterForExample.getColorOverlay_depth(), mFilterForExample.getColorOverlay_red(), mFilterForExample.getColorOverlay_green(),
                        mFilterForExample.colorOverlay_blue);}
            }
        });
        binding.constColorOverlayGreenSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float x = ((float)i)/1000;
                mFilterForExample.setColorOverlay_green(x);
                colorOverlay_green = x;
                binding.constColorOverlayGreenValue.setText(Float.toString(x));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette,
                        mFilterForExample.getColorOverlay_depth(), mFilterForExample.getColorOverlay_red(), mFilterForExample.getColorOverlay_green(),
                        mFilterForExample.colorOverlay_blue);}
            }
        });
        binding.constColorOverlayBlueSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float x = ((float)i)/1000;
                mFilterForExample.setColorOverlay_blue(x);
                colorOverlay_blue = x;
                binding.constColorOverlayBlueValue.setText(Float.toString(x));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!isAnyTask) {isAnyTask = true;setFilteredBitmap(contrast, saturation, colorOverlay, brightness, vignette,
                        mFilterForExample.getColorOverlay_depth(), mFilterForExample.getColorOverlay_red(), mFilterForExample.getColorOverlay_green(),
                        mFilterForExample.colorOverlay_blue);}
            }
        });
    }

    @Override
    public void onStop() {
        prefs.edit().putString("name_of_example_filter_1", binding.titleEditOnFiltersEditor.getText().toString()).commit();
        super.onStop();
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;
    }
    //Contrast, ToneCurve, Saturation, ColorOverlay, Brightness, Vignette
    public void setFilteredBitmap(float contrast_1,float saturation_1, float colorOverlay_1, int brightness_1, int vignette_1,
                                  int alpha, float red, float green, float blue){
            new Thread() {
                public void run() {
                    Filter myFilter = new Filter();
                    myFilter.addSubFilter(new ContrastSubFilter(contrast_1));
                    myFilter.addSubFilter(new BrightnessSubFilter(brightness_1));
                    myFilter.addSubFilter(new SaturationSubFilter(saturation_1));
                    myFilter.addSubFilter(new VignetteSubFilter(getContext(), vignette_1));
                    myFilter.addSubFilter(new ColorOverlaySubFilter(alpha,red,green,blue));

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
            mFilterForExample.setContrast(contrast_1);
            mFilterForExample.setSaturation(saturation_1);
            mFilterForExample.setBrightness(brightness_1);
            mFilterForExample.setVignette(vignette_1);

            mFilterForExample.saveFilter(prefs, getActivity(), prefs);
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
    //todo b/w filter
//    mBitmap = mBitmap.copy( Bitmap.Config.ARGB_8888 , true);
//
//    // Get size of the Bitmap
//    int width = mBitmap.getWidth();
//    int height = mBitmap.getHeight();
//
//
//                for(int i = 0; i < width; i++){
//        for(int j = 0; j < height; j++){
//
//            // Get RGB values of the current pixel
//            int currentPixel = mBitmap.getPixel(i, j);
//            int redValue = Color.red(currentPixel);
//            int blueValue = Color.blue(currentPixel);
//            int greenValue = Color.green(currentPixel);
//
//            // Get the average value of RGB values
//            int newColorValue = (int) ((redValue + blueValue + greenValue) / 3);
//
//            // Updating values of the current pixel.
//            // if values of RGB are equal, then resulting color will be Black & White
//            mBitmap.setPixel(i, j, Color.rgb(newColorValue, newColorValue, newColorValue));
//        }
//    }

    // Setting the updated Bitmap to the imageView
     //           mImageView.setImageBitmap(mBitmap);
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