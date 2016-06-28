package com.navrug.mrnorm.tests;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.navrug.mrnorm.activity.Activity;


public class SurfaceViewTest extends Activity {

  private FastRenderView renderView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    renderView = new FastRenderView(this);
    setContentView(renderView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    renderView.resume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    renderView.pause();
  }
}
