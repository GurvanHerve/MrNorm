package com.navrug.mrnorm.interfaces;

import java.util.List;

public interface Input {

  class KeyEvent {
    public static final int KEY_DOWN = 0;
    public static final int KEY_UP = 1;

    public int type;
    public int keyCode;
    public int keyChar;
  }

  class TouchEvent {
    public static final int TOUCH_DOWN = 0;
    public static final int TOUCH_UP = 1;
    public static final int TOUCH_DRAGGED = 2;

    public int type;
    public int x,y;
    public int pointer;
  }

  boolean isKeyPressed(int keyCode);
  boolean isTouchDown(int pointer);
  int getTouchX(int pointer);
  int getTouchY(int pointer);
  float getAccelX();
  float getAccelY();
  float getAccelZ();
  List<KeyEvent> getKeyEvents();
  List<TouchEvent> getTouchEvents();
}
