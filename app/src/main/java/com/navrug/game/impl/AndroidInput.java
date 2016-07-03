package com.navrug.game.impl;

import android.content.Context;
import android.os.Build;
import android.view.View;

import com.navrug.game.interfaces.Input;
import com.navrug.game.interfaces.TouchHandler;

import java.util.List;


public class AndroidInput implements Input {
  private AccelerometerHandler _accelHandler;
  private KeyboardHandler _keyHandler;
  private TouchHandler _touchHandler;

  public AndroidInput(Context context, View view, float scaleX, float scaleY) {
    _accelHandler = new AccelerometerHandler(context);
    _keyHandler = new KeyboardHandler(view);
    if (Build.VERSION.SDK_INT < 5) {
      _touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
    } else {
      _touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
    }
  }

  @Override
  public boolean isKeyPressed(int keyCode) {
    return _keyHandler.isKeyPressed(keyCode);
  }

  @Override
  public boolean isTouchDown(int pointer) {
    return _touchHandler.isTouchDown(pointer);
  }

  @Override
  public int getTouchX(int pointer) {
    return _touchHandler.getTouchX(pointer);
  }

  @Override
  public int getTouchY(int pointer) {
    return _touchHandler.getTouchY(pointer);
  }

  @Override
  public float getAccelX() {
    return _accelHandler.getAccelX();
  }

  @Override
  public float getAccelY() {
    return _accelHandler.getAccelY();
  }

  @Override
  public float getAccelZ() {
    return _accelHandler.getAccelZ();
  }

  @Override
  public List<KeyEvent> getKeyEvents() {
    return _keyHandler.getKeyEvents();
  }

  @Override
  public List<TouchEvent> getTouchEvents() {
    return _touchHandler.getTouchEvents();
  }
}
