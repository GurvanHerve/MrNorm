package com.navrug.game.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class CompassHandler implements SensorEventListener {
  private float _yaw;
  private float _pitch;
  private float _roll;

  public CompassHandler(Context context) {
    SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    if (manager.getSensorList(Sensor.TYPE_ORIENTATION).size() != 0) {
      Sensor compass = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
      manager.registerListener(this, compass, SensorManager.SENSOR_DELAY_GAME);
    }
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    _yaw = event.values[0];
    _pitch = event.values[1];
    _roll = event.values[2];
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // nothing to do here
  }

  public float getYaw() {
    return _yaw;
  }

  public float getPitch() {
    return _pitch;
  }

  public float getRoll() {
    return _roll;
  }
}
