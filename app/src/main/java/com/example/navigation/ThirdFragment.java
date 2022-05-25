package com.example.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.databinding.FragmentFragment2Binding;
import com.example.navigation.databinding.FragmentFragment3Binding;
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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;


public class ThirdFragment extends Fragment {
    SharedPreferences prefs = null;
    private FragmentFragment3Binding binding;
    int a;
    Button zxc;
    ArrayList<FullFilter> filters = new ArrayList<FullFilter>();
    ArrayList<Bitmap> photos = new ArrayList<Bitmap>();
    ImageView img;
    RecyclerView recyclerView;
    Button toNewFragment;
    Bitmap experimentBitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_fragment3, container, false);
        binding = FragmentFragment3Binding.inflate(inflater, container, false);
        Activity a = getActivity();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        Bitmap bitmap1 = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.primer);
        Bitmap bitmap2 = loadImageFromStorage();
        Bitmap bitmap3 = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.primer);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.primer);
        setFilteredBitmap(bitmap1, 20f,23f,22,25,
                0,0,0,0);
        bitmap1 = experimentBitmap;
        Toast.makeText(getActivity(),"setted", Toast.LENGTH_SHORT).show();
        setFilteredBitmap(bitmap2,0f,53f,12,
                15,0,0,0,0);
        bitmap2 =experimentBitmap ;

        setFilteredBitmap(bitmap3,40f,43f,42,45, 50,2,2,0);
        bitmap3 = experimentBitmap;
        setFilteredBitmap(bitmap4,40f,43f,42,45, 50,2,2,0);
        bitmap4 = experimentBitmap;
        int n = prefs.getInt("number_of_filters", 4);
        for(int i=0; i<n-4;i++){
            String s = prefs.getString(Integer.toString(i), "0");
            String pr = prefs.getString(s, "0");
            FullFilter f = new FullFilter();
            f.getFilter(prefs, pr);
            filters.add(f);
            Bitmap b = BitmapFactory.decodeResource(getActivity().getResources(),
                    R.drawable.primer);
            setFilteredBitmap(b,f.getContrast(),f.getSaturation(), f.getBrightness(),
                    f.getVignette(),f.getColorOverlay_depth(),f.getColorOverlay_red(),
                    f.getColorOverlay_green(),f.getColorOverlay_blue());
            b = experimentBitmap;
            photos.add(b);
        }

        filters.add(new FullFilter(a,"filter_1","0", 0f,53f,12,15));
        editor.putString("0", "0");
        filters.add(new FullFilter(a,"filter_2","1", 20f,23f,22,25));
        editor.putString("1", "1");
        filters.add(new FullFilter(a,"filter_3","2", 30f,33f,32,35));
        editor.putString("2", "2");
        filters.add(new FullFilter(a,"filter_4","3", 40f,43f,42,45));
        editor.putString("3", "3");
        editor.commit();
        Toast.makeText(getActivity(), "4", Toast.LENGTH_SHORT).show();
        photos.add(bitmap1);
        photos.add(bitmap2);
        photos.add(bitmap3);
        photos.add(bitmap4);

        return binding.getRoot();
    }

    public ThirdFragment() {
    }

    public ThirdFragment(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences shared = getActivity().getSharedPreferences("pref_filter_f", Context.MODE_PRIVATE);
        Toast.makeText(getActivity(), "onViewCreated", Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        AdapterRecyclerView adapter = new AdapterRecyclerView(filters, photos);
        recyclerView.setAdapter(adapter);

        getParentFragmentManager().setFragmentResultListener("dataFrom1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                FullFilter f = (FullFilter) result.getSerializable("df1");
                filters.add(f);
                Bitmap bt = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.primer);
                setFilteredBitmap(bt,f.getContrast(),f.getSaturation(), f.getBrightness(),
                        f.getVignette(),f.getColorOverlay_depth(),f.getColorOverlay_red(),
                        f.getColorOverlay_green(),f.getColorOverlay_blue());
                photos.add(experimentBitmap);
                adapter.photos = photos;
                adapter.filters = filters;
                adapter.notifyDataSetChanged();
            }
        });


        a=0;
//todo не удалять

//        zxc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
//                        R.drawable.primer);
//                Bitmap image = icon.copy(Bitmap.Config.ARGB_8888, true);
//                //первый простой конструктор
//                FullFilter f = new FullFilter(getActivity(),"pref_filter_f");
//                f.setBrightness(5);
//                f.setContrast(35.5f);
//                f.saveFilter(f, getActivity());
//                f.getFilter(shared,"pref_filter_f");
//                //пример с вторым конструктором, который со всеми фильтрами из zomato
//                FullFilter mFilter = new FullFilter(getActivity(),"mFilter", "shared_preferences_filter_mFilter", 1.2f,1.3f,100, .2f, .2f, .0f,
//                        0, 0,175, 139,255, 255,30,100);
//                Filter newMyFilter = new Filter();
//                newMyFilter.addSubFilter(new ContrastSubFilter(mFilter.getContrast()));
//
//                Filter myFilter = new Filter();
//                myFilter.addSubFilter(new ColorOverlaySubFilter(120, .0f, .2f, .2f));
//                Bitmap outputImage = myFilter.processFilter(image);
//
//
//                Toast.makeText(getActivity(), "Saved Bitmap", Toast.LENGTH_SHORT).show();
//                //я добавил сюда 4 метода, по названию понятно, что они делают, без слова Filtered будут сохранять вместо картинки на главном экране
//                //с словом Filtered соответственно для сохранения отфильтрованных, в чём прикол?  фотку можно сохранять только одну и она заменит прошлую,
//                //в этой папке(фильтрованных или нет), если нужно много сейвить, могу сделать, но тогда к каждой нужно будет знать путь, пока так
//            }
//        });







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
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.openNewFragment,fragment);
        fragmentTransaction.commit();

    }

    public void setFilteredBitmap(Bitmap bitmap,float contrast_1,float saturation_1, int brightness_1, int vignette_1,
                                  int alpha, float red, float green, float blue){
                experimentBitmap = bitmap;
                Filter myFilter = new Filter();
                myFilter.addSubFilter(new ContrastSubFilter(contrast_1));
                myFilter.addSubFilter(new BrightnessSubFilter(brightness_1));
                myFilter.addSubFilter(new SaturationSubFilter(saturation_1));
                myFilter.addSubFilter(new VignetteSubFilter(getContext(), vignette_1));
                myFilter.addSubFilter(new ColorOverlaySubFilter(alpha,red,green,blue));

                Bitmap image = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap bitm = myFilter.processFilter(image);
                experimentBitmap=bitm;
    }
}