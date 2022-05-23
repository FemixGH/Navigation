package com.example.navigation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.navigation.databinding.ActivityCameraBinding;
import com.example.navigation.databinding.ActivityMainBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity {
    ImageView capturedPhoto_cameraActivity;
    Button takePhoto_cameraActivity, choosePhoto_cameraActivity, cancelPhoto_cameraActivity, back_to_main;

    Bitmap bitmap;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;

    private ActivityCameraBinding binding;
    private ImageCapture imageCapture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        capturedPhoto_cameraActivity = binding.imageCapturedCameraActivity;
        takePhoto_cameraActivity = binding.takePhotoButtonCameraActivity;
        choosePhoto_cameraActivity = binding.choosePhotoCameraActivity;
        cancelPhoto_cameraActivity = binding.cancelButtonCameraActivity;
        back_to_main = binding.buttonBackToMainActivity;
        previewView = binding.cameraPreview;
        binding.buttonBackToMainActivity.setVisibility(View.VISIBLE);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
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

        takePhoto_cameraActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        Preview preview = new Preview.Builder().build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);
    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    private void bindCamera(ActivityCameraBinding binding){
        binding.imageCapturedCameraActivity.setVisibility(View.INVISIBLE);
        binding.choosePhotoCameraActivity.setVisibility(View.INVISIBLE);
        binding.cancelButtonCameraActivity.setVisibility(View.INVISIBLE);
        binding.takePhotoButtonCameraActivity.setVisibility(View.VISIBLE);
        binding.cameraPreview.setVisibility(View.VISIBLE);
    }

    private void bindPhoto(ActivityCameraBinding binding){
        binding.imageCapturedCameraActivity.setVisibility(View.VISIBLE);
        binding.choosePhotoCameraActivity.setVisibility(View.VISIBLE);
        binding.cancelButtonCameraActivity.setVisibility(View.VISIBLE);
        binding.takePhotoButtonCameraActivity.setVisibility(View.INVISIBLE);
        binding.cameraPreview.setVisibility(View.INVISIBLE);
    }

}
