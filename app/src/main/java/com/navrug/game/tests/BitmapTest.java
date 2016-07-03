package com.navrug.game.tests;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.navrug.game.activity.Activity;


public class BitmapTest extends Activity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(new BitmapView(this));
  }
}
