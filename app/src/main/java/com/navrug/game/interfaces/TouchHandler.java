package com.navrug.game.interfaces;

import android.view.View;

import java.util.List;

public interface TouchHandler extends View.OnTouchListener {
  boolean isTouchDown(int pointer);
  int getTouchX(int pointer);
  int getTouchY(int pointer);
  List<Input.TouchEvent> getTouchEvents();
}
