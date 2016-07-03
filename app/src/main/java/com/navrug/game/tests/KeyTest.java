package com.navrug.game.tests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;


public class KeyTest extends FragmentActivity implements View.OnKeyListener {

  private StringBuilder sb = new StringBuilder();
  private TextView tv;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    tv = new TextView(this);
    tv.setText("Press keys if you have some");
    tv.setOnKeyListener(this);
    tv.setFocusableInTouchMode(true);
    tv.requestFocus();
    setContentView(tv);
  }

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {
    sb.setLength(0);
    switch (event.getAction()) {
      case KeyEvent.ACTION_DOWN:
        sb.append("down, ");
        break;
      case KeyEvent.ACTION_UP:
        sb.append("up, ");
        break;
    }

    sb.append(event.getKeyCode());
    sb.append(", ");
    sb.append(event.getUnicodeChar());
    tv.setText(sb.toString());

    return event.getKeyCode() != KeyEvent.KEYCODE_BACK;
  }
}
