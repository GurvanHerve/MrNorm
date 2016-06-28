package com.navrug.mrnorm.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class AndroidFastRenderView extends SurfaceView implements Runnable {
  private AndroidGame _game;
  private Bitmap _framebuffer;
  private Thread _renderThread;
  private SurfaceHolder _holder;
  private volatile boolean _running = false;

  AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) {
    super(game);
    this._game = game;
    this._framebuffer = framebuffer;
    this._holder = getHolder();
  }

  void resume() {
    _running = true;
    _renderThread = new Thread(this);
    _renderThread.start();
  }

  void pause() {
    _running = false;
    while (true) {
      try {
        _renderThread.join();
      } catch (InterruptedException e) {
        // retry
      }
    }
  }

  @Override
  public void run() {
    Rect dstRect = new Rect();
    long startTime = System.nanoTime();
    while (_running) {
      if (!_holder.getSurface().isValid()) {
        continue;
      }

      float deltaTime = (System.nanoTime() - startTime) / 1000000000.0F;
      startTime = System.nanoTime();

      _game.getCurrentScreen().update(deltaTime);
      _game.getCurrentScreen().present(deltaTime);

      Canvas canvas = _holder.lockCanvas();
      canvas.getClipBounds(dstRect);
      canvas.drawBitmap(_framebuffer, null, dstRect, null);
      _holder.unlockCanvasAndPost(canvas);
    }
  }
}
