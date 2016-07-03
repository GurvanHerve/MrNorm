package com.navrug.game.impl;

import android.view.MotionEvent;
import android.view.View;

import com.navrug.game.interfaces.Input;
import com.navrug.game.interfaces.TouchHandler;
import com.navrug.game.utils.Pool;

import java.util.ArrayList;
import java.util.List;


public class MultiTouchHandler implements TouchHandler {
  private static final int MAX_TOUCHPOINTS = 10;

  private boolean[] _isTouched = new boolean[MAX_TOUCHPOINTS];
  private int[] _touchX = new int[MAX_TOUCHPOINTS];
  private int[] _touchY = new int[MAX_TOUCHPOINTS];
  private int[] _id = new int[MAX_TOUCHPOINTS];
  private Pool<Input.TouchEvent> _touchEventPool;
  private List <Input.TouchEvent> _touchEvents = new ArrayList<>();
  private List <Input.TouchEvent> _touchEventsBuffer = new ArrayList <> ();
  private float _scaleX;
  private float _scaleY;

  public MultiTouchHandler(View view, float scaleX, float scaleY) {
    Pool.PoolObjectFactory<Input.TouchEvent> factory = new Pool.PoolObjectFactory<Input.TouchEvent>() {
      public Input.TouchEvent createObject() {
        return new Input.TouchEvent();
      }
    };
    _touchEventPool = new Pool <Input.TouchEvent> (factory, 100);
    view.setOnTouchListener(this);
    this._scaleX = scaleX;
    this._scaleY = scaleY;
  }

  @Override
  public boolean isTouchDown(int pointer) {
    synchronized (this) {
      int index = getIndex(pointer);
      if (index < 0 || index >= MAX_TOUCHPOINTS)
        return false;
      else
        return _isTouched[index];
    }
  }

  @Override
  public int getTouchX(int pointer) {
    synchronized (this) {
      int index = getIndex(pointer);
      if (index < 0 || index >= MAX_TOUCHPOINTS)
        return 0;
      else
        return _touchX[index];
    }
  }

  @Override
  public int getTouchY(int pointer) {
    synchronized (this) {
      int index = getIndex(pointer);
      if (index < 0 || index >= MAX_TOUCHPOINTS)
        return 0;
      else
        return _touchY[index];
    }
  }

  @Override
  public List<Input.TouchEvent> getTouchEvents() {
    synchronized (this) {
      int len = _touchEvents.size();
      for (int i = 0; i < len; i++) {
        _touchEventPool.free(_touchEvents.get(i));
      }
      _touchEvents.clear();
      _touchEvents.addAll(_touchEventsBuffer);
      _touchEventsBuffer.clear();
      return _touchEvents;
    }
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    synchronized (this) {
      int action = event.getAction() & MotionEvent.ACTION_MASK;
      int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
      int pointerCount = event.getPointerCount();
      Input.TouchEvent touchEvent;
      for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
        if (i >= pointerCount) {
          _isTouched[i] = false;
          _id[i] = -1;
          continue;
        }
        int pointerId = event.getPointerId(i);
        if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
          // if it's an up/down/cancel/out event, mask the id to see if we should process it for this touch point
          continue;
        }
        switch (action) {
          case MotionEvent.ACTION_DOWN:
          case MotionEvent.ACTION_POINTER_DOWN:
            touchEvent = _touchEventPool.newObject();
            touchEvent._type = Input.TouchEvent.TOUCH_DOWN;
            touchEvent.pointer = pointerId;
            touchEvent._x = _touchX[i] = (int) (event.getX(i) * _scaleX);
            touchEvent._y = _touchY[i] = (int) (event.getY(i) * _scaleY);
            _isTouched[i] = true;
            _id[i] = pointerId;
            _touchEventsBuffer.add(touchEvent);
            break;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_POINTER_UP:
          case MotionEvent.ACTION_CANCEL:
            touchEvent = _touchEventPool.newObject();
            touchEvent._type = Input.TouchEvent.TOUCH_UP;
            touchEvent.pointer = pointerId;
            touchEvent._x = _touchX[i] = (int) (event.getX(i) * _scaleX);
            touchEvent._y = _touchY[i] = (int) (event.getY(i) * _scaleY);
            _isTouched[i] = false;
            _id[i] = -1;
            _touchEventsBuffer.add(touchEvent);
            break;
          case MotionEvent.ACTION_MOVE:
            touchEvent = _touchEventPool.newObject();
            touchEvent._type = Input.TouchEvent.TOUCH_DRAGGED;
            touchEvent.pointer = pointerId;
            touchEvent._x = _touchX[i] = (int) (event.getX(i) * _scaleX);
            touchEvent._y = _touchY[i] = (int) (event.getY(i) * _scaleY);
            _isTouched[i] = true;
            _id[i] = pointerId;
            _touchEventsBuffer.add(touchEvent);
            break;
        }
      }
      return true;
    }
  }

  // returns the index for a given pointerId or −1 if no index.
  private int getIndex(int pointerId) {
    for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
      if (_id[i] == pointerId) {
        return i;
      }
    }
    return -1;
  }
}
