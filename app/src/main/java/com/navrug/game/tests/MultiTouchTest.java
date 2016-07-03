package com.navrug.game.tests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class MultiTouchTest extends FragmentActivity implements View.OnTouchListener {
  private StringBuilder sb = new StringBuilder();
  private TextView tv;
  private float[] x = new float[10];
  private float[] y = new float[10];
  private boolean[] touched = new boolean[10];
  private int[] id = new int[10];

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    tv = new TextView(this);
    tv.setText("Touch and drag multiple fingers");
    tv.setOnTouchListener(this);
    setContentView(tv);
    for (int i = 0; i < 10; i++) {
      id[i] = i;
    }
    updateTextView();
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    int action = event.getAction() & MotionEvent.ACTION_MASK;
    int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
    int pointerCount = event.getPointerCount();

    for (int i = 0; i < 10; i++) {
      if (i >= pointerCount) {
        touched[i] = false;
        id[i] = -1;
        continue;
      }

      if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
        continue;
      }

      int pointerId = event.getPointerId(i);
      switch (action) {
        case MotionEvent.ACTION_DOWN:
        case MotionEvent.ACTION_POINTER_DOWN:
          touched[i] = true;
          id[i] = pointerId;
          x[i] = event.getX(i);
          y[i] = event.getY(i);
          break;

        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        case MotionEvent.ACTION_OUTSIDE:
        case MotionEvent.ACTION_CANCEL:
          touched[i] = false;
          id[i] = -1;
          x[i] = event.getX(i);
          y[i] = event.getY(i);
          break;

        case MotionEvent.ACTION_MOVE:
          touched[i] = true;
          id[i] = pointerId;
          x[i] = event.getX(i);
          y[i] = event.getY(i);
          break;
      }
    }

    updateTextView();

    return true;
  }

  private void updateTextView() {
    sb.setLength(0);
    for (int i = 0; i < 10; i++) {
      sb.append(touched[i]);
      sb.append(", ");
      sb.append(x[i]);
      sb.append(", ");
      sb.append(y[i]);
      sb.append("\n");
    }
    tv.setText(sb.toString());
  }
}
