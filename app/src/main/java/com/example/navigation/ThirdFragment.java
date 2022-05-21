package com.example.navigation;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;

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

    Button zxc;
    String[] nameOfFilters = {"asdasd", "asdasd", "asdasd"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_fragment3, container, false);

        binding = FragmentFragment3Binding.inflate(inflater, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new AdapterRecyclerView(nameOfFilters));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        zxc = binding.zxc;;
        zxc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter myFilter = new Filter();
                myFilter.addSubFilter(new BrightnessSubFilter(30));
                myFilter.addSubFilter(new ContrastSubFilter(1.1f));
                Bitmap bitmapPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.primer);
                Bitmap image = bitmapPhoto.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap outputImage = myFilter.processFilter(image);


                saveToInternalFilteredStorage(outputImage);
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
    private String saveToInternalFilteredStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "photo_filtered.jpg");
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

    private Bitmap loadImageFilteredFromStorage() {
        Bitmap b = null;
        try {
            ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
            File path1 = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f = new File(path1, "photo_filtered.jpg");
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }
    private String saveToInternalStorage(Bitmap bitmapImage) {
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

    private Bitmap loadImageFromStorage() {
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