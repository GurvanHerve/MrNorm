package com.navrug.mrnorm.tests;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;


public class AccelerometerTest extends FragmentActivity implements SensorEventListener {
  private TextView tv;
  private StringBuilder sb = new StringBuilder();
  private int screenRotation;

  private static final int[][] ACCELEROMETER_AXIS_SWAP = {
      {1, -1, 0, 1}, // ROTATION_0
      {-1, -1, 1, 0}, // ROTATION_90
      {-1, 1, 0, 1}, // ROTATION_180
      {1, 1, 1, 0} // ROTATION_270
  };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    tv = new TextView(this);
    setContentView(tv);

    SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
    if (!sensors.isEmpty()) {
      if (!sensorManager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME)) {
        return;
      }
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    screenRotation = windowManager.getDefaultDisplay().getRotation();
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    final int[] as = ACCELEROMETER_AXIS_SWAP[screenRotation];
    float screenX = as[0] * event.values[as[2]];
    float screenY = as[1] * event.values[as[3]];
    float screenZ = event.values[2];

    sb.setLength(0);
    sb.append("x: ").append(screenX).append(", y: ").append(screenY).append(", z: ").append(screenZ);
    tv.setText(sb.toString());
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {

  }
}
