package com.yohanesryan.ugd7_b_9975;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityCamera extends AppCompatActivity implements SensorEventListener {

    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_PROXIMITY) {
            if(sensorEvent.values[0] == 0) {
                Toast.makeText(ActivityCamera.this, "Kamera Belakang", Toast.LENGTH_SHORT)
                        .show();

                try {
                    mCamera = Camera.open();
                } catch (Exception e) {
                    Log.d("Error", "Failed to get camera" + e.getMessage());
                }

                if (mCamera != null) {
                    mCameraView = new CameraView(this, mCamera);
                    FrameLayout camera_view = (FrameLayout) findViewById(R.id.FLCamera);
                    camera_view.addView(mCameraView);
                }

                ImageButton imageClose = (ImageButton) findViewById(R.id.imgClose);
                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.exit(0);
                    }
                });
            }
            else {
                Toast.makeText(ActivityCamera.this, "Kamera Depan", Toast.LENGTH_SHORT)
                        .show();

                try {
                    mCamera = Camera.open(1);
                } catch (Exception e) {
                    Log.d("Error", "Failed to get camera" + e.getMessage());
                }

                if (mCamera != null) {
                    mCameraView = new CameraView(this, mCamera);
                    mCameraView.mCamera.setDisplayOrientation(90);
                    FrameLayout camera_view = (FrameLayout) findViewById(R.id.FLCamera);
                    camera_view.addView(mCameraView);
                }

                ImageButton imageClose = (ImageButton) findViewById(R.id.imgClose);
                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.exit(0);
                    }
                });
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
