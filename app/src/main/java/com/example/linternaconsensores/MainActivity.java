package com.example.linternaconsensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton toggleButton;

    boolean hasCameraFlash = false;
    boolean flashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.boton);

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasCameraFlash){
                    if(flashOn){
                        flashOn = false;
                        toggleButton.setImageResource(R.drawable.linterna_apagada_);
                        flashLightOff();
                    }
                    else{
                        flashOn = true;
                        toggleButton.setImageResource(R.drawable.linterna_encendida_);
                        flashLightOn();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "No hay flash disponible en su dispositivo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void flashLightOn(){
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            assert cameraManager != null;
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, true);
            }
            Toast.makeText(MainActivity.this, "La linterna está ENCENDIDA", Toast.LENGTH_SHORT).show();
        }
        catch(CameraAccessException e){
            Log.e("Problema de la cámara", "No se puede encender la linterna de la cámara");
        }
    }

    private void flashLightOff(){
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            assert cameraManager != null;
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, false);
            }
            Toast.makeText(MainActivity.this, "La linterna está APAGADA", Toast.LENGTH_SHORT).show();
        }
        catch(CameraAccessException e){
            Log.e("Problema de la cámara", "No se puede apagar la linterna de la cámara");
        }
    }
}