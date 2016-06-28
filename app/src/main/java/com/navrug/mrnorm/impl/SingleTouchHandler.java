package com.navrug.mrnorm.impl;

import android.view.MotionEvent;
import android.view.View;

import com.navrug.mrnorm.interfaces.Input;
import com.navrug.mrnorm.interfaces.TouchHandler;
import com.navrug.mrnorm.utils.Pool;

import java.util.ArrayList;
import java.util.List;


public class SingleTouchHandler implements TouchHandler {
  private boolean _isTouched;
  private int _touchX;
  private int _touchY;
  private Pool<Input.TouchEvent> _touchEventPool;
  private List<Input.TouchEvent> _touchEvents = new ArrayList<>();
  private List<Input.TouchEvent> _touchEventsBuffer = new ArrayList<>();
  private float _scaleX;
  private float _scaleY;

  public SingleTouchHandler(View view, float scaleX, float scaleY) {
    Pool.PoolObjectFactory<Input.TouchEvent> factory = new Pool.PoolObjectFactory<Input.TouchEvent>() {
      @Override
      public Input.TouchEvent createObject() {
        return new Input.TouchEvent();
      }
    };
    _touchEventPool = new Pool<>(factory, 100);
    view.setOnTouchListener(this);
    this._scaleX = scaleX;
    this._scaleY = scaleY;
  }

  @Override
  public boolean isTouchDown(int pointer) {
    synchronized (this) {
      if (pointer == 0)
        return _isTouched;
      else
        return false;
    }
  }

  @Override
  public int getTouchX(int pointer) {
    synchronized (this) {
      return _touchX;
    }
  }

  @Override
  public int getTouchY(int pointer) {
    synchronized (this) {
      return _touchX;
    }
  }

  @Override
  public List<Input.TouchEvent> getTouchEvents() {
    synchronized (this) {
      int len = _touchEvents.size();
      for (int i = 0; i < len; i++)
        _touchEventPool.free(_touchEvents.get(i));
      _touchEvents.clear();
      _touchEvents.addAll(_touchEventsBuffer);
      _touchEventsBuffer.clear();
      return _touchEvents;
    }
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    synchronized(this) {
      Input.TouchEvent touchEvent = _touchEventPool.newObject();
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          touchEvent.type = Input.TouchEvent.TOUCH_DOWN;
          _isTouched = true;
          break;
        case MotionEvent.ACTION_MOVE:
          touchEvent.type = Input.TouchEvent.TOUCH_DRAGGED;
          _isTouched = true;
          break;
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_UP:
          touchEvent.type = Input.TouchEvent.TOUCH_UP;
          _isTouched = false;
          break;
      }
      touchEvent.x = _touchX = (int)(event.getX() * _scaleX);
      touchEvent.y = _touchY = (int)(event.getY() * _scaleY);
      _touchEventsBuffer.add(touchEvent);
      return true;
    }
  }
}
