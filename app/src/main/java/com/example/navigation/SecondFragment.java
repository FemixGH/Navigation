package com.example.navigation;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.navigation.databinding.FragmentFragment2Binding;
import com.google.common.util.concurrent.ListenableFuture;
//import com.vader.sentiment.analyzer.SentimentAnalyzer;
//import com.vader.sentiment.analyzer.SentimentPolarities;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

//import ja.burhanrashid52.photoeditor.PhotoEditor;
//import ja.burhanrashid52.photoeditor.PhotoEditorView;

//import com.vader.sentiment.analyzer;


public class SecondFragment extends Fragment{
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
    PreviewView cameraPreview;
    boolean isCameraOpened=true;

    ActivityResultLauncher<String> mTakePhoto;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private String currentImagePath;
    private ImageCapture imageCapture;
    private ImageAnalysis imageAnalysis;


    public void setImage_2(Uri uri){
        this.image.setImageURI(uri);
    }

    private FragmentFragment2Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragment2Binding.inflate(inflater, container, false);
        binding.capturedImageSecond.setVisibility(View.INVISIBLE);
        binding.cameraPreview.setVisibility(View.VISIBLE);
        binding.takePhotoButton.setVisibility(View.VISIBLE);

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

        cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor() );

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
                if(isCameraOpened) {
                    setImageVisible(binding);
                    isCameraOpened=false;
                }else{
                    setCameraVisible(binding);
                    isCameraOpened=true;
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setText("");
            }

        });

        binding.takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                capturePhoto();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void capturePhoto() {
        File photoDir = new File("/mnt/sd");

        if(!photoDir.exists()){photoDir.mkdir();}

        Date date = new Date();
        String timestamp = String.valueOf(date.getTime());

        MethodHandle mh = StringConcatFactory.makeConcat(
                        MethodHandles.lookup(), // normally provided by the JVM
                        "foobar", // normally provided by javac, but meaningless here
                        // method type is normally provided by the JVM and matches the invocation
                        MethodType.methodType(String.class, String.class, char.class, String.class))
                .getTarget();

// we can now use the handle to perform a concatenation
// the argument types must match the MethodType specified above
        String result = (String)mh.invokeExact(arg1, arg2, arg3);
        String photoFilePath = photoDir.getAbsolutePath() + "/" + timestamp + ".jpg";

        File photoFile = new File(photoFilePath);
        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(photoFile).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {

                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        Preview preview = new Preview.Builder().build();

        preview.setSurfaceProvider(cameraPreview.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build();
        imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        cameraProvider.bindToLifecycle(getActivity(),cameraSelector,preview, imageCapture, imageAnalysis);


    }

    public void setCameraVisible(FragmentFragment2Binding binding){
        binding.capturedImageSecond.setVisibility(View.INVISIBLE);
        binding.cameraPreview.setVisibility(View.VISIBLE);
        binding.takePhotoButton.setVisibility(View.VISIBLE);
        binding.photoButton.setText("Cancel");
        binding.photoButton.setIconSize(0);
    }
    public void setImageVisible(FragmentFragment2Binding binding){
        binding.capturedImageSecond.setVisibility(View.VISIBLE);
        binding.cameraPreview.setVisibility(View.INVISIBLE);
        binding.takePhotoButton.setVisibility(View.INVISIBLE);
        binding.photoButton.setText("Open Camera");
        binding.photoButton.setIconSize(24);
    }
    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(getActivity());
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        dispatchTakePictureIntent();
        //activityResultLauncher.launch(intent);
    }

//    private File createImageFile() {
//        @SuppressLint("SimpleDateFormat") String timestamp = new SimpleDateFormat("K:mm a, z").format(new Date());
//        String imageName = "jpg_"+timestamp+"_";
//
//        File storageDir = getActivity().getExternalFilesDir((Environment.DIRECTORY_PICTURES));
//        File imageFile = null;
//        try {
//            //imageFile = File.createTempFile(imageName, ".jpg", storageDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        currentImagePath = imageFile.getAbsolutePath();
//        return imageFile;
//    }
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

    String currentPhotoPath;

    private File createImageFile(){
        // Create an image file name
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File directory = cw.getDir("imageDir_for_camera_photo", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "photo.jpg");
        currentPhotoPath = mypath.getAbsolutePath();
        return mypath;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getActivity().getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            File photoFile = null;

            photoFile = createImageFile();
            Toast.makeText(getActivity(), "File created", Toast.LENGTH_SHORT).show();

            // Continue only if the File was successfully created
            if (photoFile != null) {
                File theDir = new File("imageDir_for_camera_photo");
                if (!theDir.exists()){
                    theDir.mkdirs();
                }
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                activityResultLauncher.launch(takePictureIntent);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                Toast.makeText(getActivity(), "Camera ect", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Cameri net", Toast.LENGTH_SHORT).show();
        }

        }else{
            Toast.makeText(getActivity(), "getActivity().getApplicationContext().getPackageManager().hasSystemFeature(\n" +
                    "                PackageManager.FEATURE_CAMERA)", Toast.LENGTH_SHORT).show();
        }
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }
    private void setPic() {
        // Get the dimensions of the View
        int targetW = image.getWidth();
        int targetH = image.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        image.setImageBitmap(bitmap);
    }


}