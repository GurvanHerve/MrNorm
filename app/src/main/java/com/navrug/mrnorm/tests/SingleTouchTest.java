package com.navrug.mrnorm.tests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class SingleTouchTest extends FragmentActivity implements View.OnTouchListener {

  private StringBuilder sb = new StringBuilder();
  private TextView tv;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    tv = new TextView(this);
    tv.setText("Touch and drag only one finger !");
    tv.setOnTouchListener(this);
    setContentView(tv);
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    sb.setLength(0);
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        sb.append("down, ");
        break;
      case MotionEvent.ACTION_MOVE:
        sb.append("move, ");
        break;
      case MotionEvent.ACTION_CANCEL:
        sb.append("cancel, ");
        break;
      case MotionEvent.ACTION_UP:
        sb.append("up, ");
        break;
    }
    sb.append(event.getX()).append(", ").append(event.getY());
    tv.setText(sb.toString());

    return true;
  }
}
