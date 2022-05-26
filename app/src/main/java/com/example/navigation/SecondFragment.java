package com.example.navigation;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.example.navigation.databinding.FragmentFragment2Binding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;


public class SecondFragment extends Fragment {
    Button search, gallery, back, camera, clear, gallery_camera, open_camera, take_photo_button, rotate_button;
    EditText edit;
    TextView testMention;
    Bitmap bitmap;

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String TEXT = "text";
    public ImageView image;
    public Uri mPhoto;
    PreviewView cameraPreview;
    boolean isCameraOpened = false;
    Button saveToGallery;
    Button reload;
    ActivityResultLauncher<String> mTakePhoto;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    public Bitmap copyBitmap;


    private FragmentFragment2Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFragment2Binding.inflate(inflater, container, false);
        setImageVisible(binding);

        return binding.getRoot();
    }

    public void setUri(Uri u) {
        this.mPhoto = u;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        cameraPreview = binding.cameraPreview;
        gallery_camera = binding.galleryWithCameraView;
        open_camera = binding.openCamera;
        edit = binding.SearchTextOn2;
        take_photo_button = binding.takePhotoButton;
        rotate_button = binding.rotateButton;
        ConstraintLayout c = binding.myId1;
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        image = binding.capturedImageSecond;
        back = binding.backToImage;
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
        }, getExecutor());

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    image.setImageBitmap(bitmap);
                    saveToInternalStorage(bitmap);
                    MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, String.valueOf(System.currentTimeMillis()), "");
                }
            }
        });


        bitmap = loadImageFromStorage();
        if (bitmap != null) {
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
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("text", edit.getText().toString());
                editor.apply();
                Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show();

                String text = sharedPreferences.getString("text", null);

                testMention.setText(text);

                String textSample = "Strange that I did not know him then,hat friend of mine! I did not even show him then One friendly sign";
            }
        });

        rotate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = loadImageFromStorage();
                bitmap = RotateBitmap(bitmap, 90);
                image.setImageBitmap(bitmap);
                saveToInternalStorage(bitmap);
            }
        });

        copyBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        reload = binding.reload;
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromStorage();
                 bitmap = copyBitmap.copy(Bitmap.Config.ARGB_8888, true);
                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
            }
        });



        if (bitmap == null) {
            rotate_button.setVisibility(View.GONE);
        } else rotate_button.setVisibility(View.VISIBLE);

        gallery = binding.gallery;
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTakePhoto.launch("image/*");

            }
        });

        gallery_camera.setOnClickListener(new View.OnClickListener() {
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

                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result);
                            bitmap = RotateBitmap(bitmap, 90);

                            saveToInternalStorage(bitmap);
                            Toast.makeText(getActivity(), "gallery saved", Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setImageVisible(binding);

                    }
                }
        );

        saveToGallery = binding.saveInGallery;
        saveToGallery.setOnClickListener(v -> SaveImage(bitmap));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "camera", Toast.LENGTH_SHORT).show();
                if (isCameraOpened) {
                    setImageVisible(binding);
                    isCameraOpened = false;
                } else {
                    setCameraVisible(binding);
                    isCameraOpened = true;
                }
            }
        });

        open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCameraOpened) {
                    setImageVisible(binding);
                    isCameraOpened = false;
                } else {
                    setCameraVisible(binding);
                    isCameraOpened = true;
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

        long timestamp = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");


        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(
                        getActivity().getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                ).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Toast.makeText(getActivity(), "Photo have been saved", Toast.LENGTH_SHORT).show();
                        Uri mImageCaptureUri = outputFileResults.getSavedUri();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageCaptureUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        bitmap = RotateBitmap(bitmap, 90);
                        saveToInternalStorage(bitmap);
                        image.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {

                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
        );
        setImageVisible(binding);
    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        Preview preview = new Preview.Builder()
                .build();
        preview.setSurfaceProvider(cameraPreview.getSurfaceProvider());
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();
        cameraProvider.bindToLifecycle((LifecycleOwner) getActivity(), cameraSelector, imageCapture, preview);
    }

    public void setCameraVisible(FragmentFragment2Binding binding) {

        binding.scroll.setVisibility(View.GONE);
        binding.constraintWithPreview.setVisibility(View.VISIBLE);
        binding.Con2.setVisibility(View.GONE);
        binding.galleryWithCameraView.setVisibility(View.VISIBLE);
        binding.capturedImageSecond.setVisibility(View.GONE);
        binding.cameraPreview.setVisibility(View.VISIBLE);
        binding.takePhotoButton.setVisibility(View.VISIBLE);
        binding.gallery.setVisibility(View.GONE);
        take_photo_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                capturePhoto();
            }
        });
    }


    public void setImageVisible(FragmentFragment2Binding binding) {

        binding.scroll.setVisibility(View.VISIBLE);
        binding.constraintWithPreview.setVisibility(View.GONE);
        binding.Con2.setVisibility(View.VISIBLE);
        binding.capturedImageSecond.setVisibility(View.VISIBLE);
        binding.cameraPreview.setVisibility(View.GONE);
        binding.takePhotoButton.setVisibility(View.GONE);
        binding.gallery.setVisibility(View.VISIBLE);
        binding.galleryWithCameraView.setVisibility(View.GONE);

    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(getActivity());
    }


    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "photo.jpg");
        String path = mypath.getAbsolutePath();
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

    private File createImageFile() {
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
                if (!theDir.exists()) {
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

        } else {
            Toast.makeText(getActivity(), "getActivity().getApplicationContext().getPackageManager().hasSystemFeature(\n" +
                    "                PackageManager.FEATURE_CAMERA)", Toast.LENGTH_SHORT).show();
        }
    }


    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();

        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaScannerConnection.scanFile(getActivity(), new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

        Toast.makeText(getActivity(), "saved successfully", Toast.LENGTH_SHORT).show();
    }


}