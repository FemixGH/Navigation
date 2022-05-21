package com.example.navigation;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.navigation.databinding.FragmentFragment2Binding;
//import com.vader.sentiment.analyzer.SentimentAnalyzer;
//import com.vader.sentiment.analyzer.SentimentPolarities;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//import ja.burhanrashid52.photoeditor.PhotoEditor;
//import ja.burhanrashid52.photoeditor.PhotoEditorView;

//import com.vader.sentiment.analyzer;


public class SecondFragment extends Fragment {
    Button search,gallery, filter, camera, clear;
    EditText edit;
    TextView testMention;
    Bitmap bitmap;
    private final String mKEY_TEXT = "myKey_text";
    private final String mKEY_PHOTO = "myKey_photo";
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String SHARED_PREFS_PHOTO = "sharedPrefs_photo";
    private static final String TEXT = "text";
    public  ImageView image;
    public Uri mPhoto;
    public Uri newUri;


    ActivityResultLauncher<String> mTakePhoto;
    ActivityResultLauncher<Intent> activityResultLauncher;

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
    public void setUri(Uri u){
        this.mPhoto=u;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        //System.loadLibrary("NativeImageProcessor");
        edit = binding.SearchTextOn2;
        ConstraintLayout c = binding.myId1;
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        image = binding.capturedImageSecond;
        camera = binding.photoButton;
        clear = binding.clearText;

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    image.setImageBitmap(bitmap);
                    saveToInternalStorage(bitmap);
                    MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, String.valueOf(System.currentTimeMillis()), "");
                }
            }
        });
        //PhotoEditorView mPhotoEditorView = binding.photoEditorView;
//todo new library .....


        bitmap = loadImageFromStorage();
        if(bitmap!=null){
            image.setImageBitmap(bitmap);
        }
        edit.setText(pref.getString("text", null));

        edit.setOnFocusChangeListener((view1, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            }
        });

        testMention = binding.testMention;

        search = binding.button2;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("text", edit.getText().toString());
                editor.apply();
                Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();

                String text = sharedPreferences.getString("text", null);

                testMention.setText(text);




                String textSample = "Strange that I did not know him then,hat friend of mine! I did not even show him then One friendly sign";

//                final SentimentPolarities sentimentPolarities =
//                        SentimentAnalyzer.getScoresFor("that's a rare and valuable feature.");
//                System.out.println(sentimentPolarities);



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

                        //todo gallery sharedPref
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result);
                            saveToInternalStorage(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }


        );

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setText("");
            }

        });



    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncher.launch(intent);
    }


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos =new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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