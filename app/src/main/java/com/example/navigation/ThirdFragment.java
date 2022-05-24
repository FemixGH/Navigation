package com.example.navigation;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.databinding.FragmentFragment2Binding;
import com.example.navigation.databinding.FragmentFragment3Binding;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;

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
    private FragmentFragment3Binding binding;
    int a;
    Button zxc;
    String[] nameOfFilters = {"asdasd", "asdasd", "asdasd"};
    ImageView img;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_fragment3, container, false);
        binding = FragmentFragment3Binding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity(), "onViewCreated", Toast.LENGTH_SHORT).show();
        img = binding.simpleImage;
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new AdapterRecyclerView(nameOfFilters));
        a=0;
        zxc = binding.buttonOnFiltersScreen;
        zxc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //первый простой конструктор
                FullFilter f = new FullFilter(getActivity(),"filter_f");
                f.setBrightness(5);
                f.setContrast(35.5f);
                f.saveFilter(f);
                //пример с вторым конструктором, который со всеми фильтрами из zomato
                FullFilter mFilter = new FullFilter(getActivity(),"mFilter", "shared_preferences_filter_mFilter", 1.2f,1.3f,100, .2f, .2f, .0f,
                        0, 0,175, 139,255, 255,30,100);
                Filter newMyFilter = new Filter();
                newMyFilter.addSubFilter(new ContrastSubFilter(mFilter.getContrast()));

                Toast.makeText(getActivity(), "Saved Bitmap", Toast.LENGTH_SHORT).show();
                //я добавил сюда 4 метода, по названию понятно, что они делают, без слова Filtered будут сохранять вместо картинки на главном экране
                //с словом Filtered соответственно для сохранения отфильтрованных, в чём прикол?  фотку можно сохранять только одну и она заменит прошлую,
                //в этой папке(фильтрованных или нет), если нужно много сейвить, могу сделать, но тогда к каждой нужно будет знать путь, пока так
            }
        });


        // Assume block needs to be inside a Try/Catch block.
//        String path = Environment.getExternalStorageDirectory().toString();
//
//        Integer counter = 0;
//        File file = new File(path, "LOLOLO.jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
//
//
//        //outputImage.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
//
//
//        try {
//            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//        OutputStream outStream = null;
//        File file2 = new File(extStorageDirectory, "er.PNG");
//        try {
//            outStream = new FileOutputStream(file2);
//            outputImage.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//            outStream.flush();
//            outStream.close();
//        } catch (Exception e) {
//
//        }


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