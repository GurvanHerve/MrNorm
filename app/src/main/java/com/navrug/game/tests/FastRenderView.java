package com.navrug.game.tests;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


class FastRenderView extends SurfaceView implements Runnable, SurfaceHolder.Callback, SurfaceHolder.Callback2 {

  private Thread renderThread = null;
  private SurfaceHolder holder;
  private volatile boolean running = false;

  public FastRenderView(Context context) {
    super(context);
    holder = getHolder();
    holder.addCallback(this);
  }

  void resume() {
    running = true;
    renderThread = new Thread(this);
    renderThread.start();
  }

  void pause() {
    running = false;
    while (true) {
      try {
        renderThread.join();
        return;
      } catch (InterruptedException e) {
        // retry
      }
    }
  }

  @Override
  public void run() {
    while (running) {
      if (!holder.getSurface().isValid()) {
        continue;
      }
      Canvas canvas = holder.lockCanvas();
      canvas.drawRGB(255, 0, 0);
      holder.unlockCanvasAndPost(canvas);
    }
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    Log.d("echo", "FastRenderView -- surfaceCreated");

  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    Log.d("echo", "FastRenderView -- surfaceChanged");
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    Log.d("echo", "FastRenderView -- surfaceDestroyed");
  }

  @Override
  public void surfaceRedrawNeeded(SurfaceHolder holder) {
    Log.d("echo", "FastRenderView -- surfaceRedrawNeeded");
  }
}
