package com.navrug.mrnorm.impl;

import android.view.View;

import com.navrug.mrnorm.interfaces.Input;
import com.navrug.mrnorm.utils.Pool;

import java.util.ArrayList;
import java.util.List;


public class KeyboardHandler implements View.OnKeyListener {
  private boolean[] _pressedKeys = new boolean[128];
  private Pool<Input.KeyEvent> _pool;
  private List<Input.KeyEvent> _keyEventsBuffer = new ArrayList<>();
  private List<Input.KeyEvent> _keyEvents = new ArrayList<>();

  public KeyboardHandler(View view) {
    Pool.PoolObjectFactory<Input.KeyEvent> factory = new Pool.PoolObjectFactory<Input.KeyEvent>() {
      @Override
      public Input.KeyEvent createObject() {
        return new Input.KeyEvent();
      }
    };
    _pool = new Pool<>(factory, 100);
    view.setOnKeyListener(this);
    view.setFocusableInTouchMode(true);
    view.requestFocus();
  }

  @Override
  public boolean onKey(View v, int keyCode, android.view.KeyEvent event) {
    if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE) {
      return false;
    }

    synchronized (this) {
      Input.KeyEvent keyEvent = _pool.newObject();
      keyEvent.keyCode = keyCode;
      keyEvent.keyChar = (char) event.getUnicodeChar();
      if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
        keyEvent.type = Input.KeyEvent.KEY_DOWN;
        if(keyCode > 0 && keyCode < 127)
          _pressedKeys[keyCode] = true;
      }
      if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
        keyEvent.type = Input.KeyEvent.KEY_UP;
        if(keyCode > 0 && keyCode < 127)
          _pressedKeys[keyCode] = false;
      }
      _keyEventsBuffer.add(keyEvent);
    }
    return false;
  }

  public boolean isKeyPressed(int keyCode) {
    if (keyCode < 0 || keyCode > 127) {
      return false;
    }
    return _pressedKeys[keyCode];
  }

  public List<Input.KeyEvent> getKeyEvents() {
    synchronized (this) {
      int len = _keyEvents.size();
      for (int i = 0; i < len; i++) {
        _pool.free(_keyEvents.get(i));
      }
      _keyEvents.clear();
      _keyEvents.addAll(_keyEventsBuffer);
      _keyEventsBuffer.clear();
      return _keyEvents;
    }
  }
}
