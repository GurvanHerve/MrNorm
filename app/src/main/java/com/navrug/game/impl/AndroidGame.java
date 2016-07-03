package com.navrug.game.impl;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;

import com.navrug.game.interfaces.Audio;
import com.navrug.game.interfaces.FileIO;
import com.navrug.game.interfaces.Game;
import com.navrug.game.interfaces.Graphics;
import com.navrug.game.interfaces.Input;
import com.navrug.game.interfaces.Screen;

public class AndroidGame extends AppCompatActivity implements Game {
  private AndroidFastRenderView _renderView;
  private Graphics _graphics;
  private Audio _audio;
  private Input _input;
  private FileIO _fileIO;
  private Screen _screen;
  private PowerManager.WakeLock _wakelock;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Manage orientation => XML !!!
    boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    int frameBufferWidth = isLandscape ? 1080 : 1920;
    int frameBufferHeight = isLandscape ? 1920 : 1080;
    Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);

    Display display = getWindowManager().getDefaultDisplay();
    Point point = new Point();
    display.getSize(point);
    float scaleX = frameBufferWidth / point.x;
    float scaleY = frameBufferHeight / point.y;

    _renderView = new AndroidFastRenderView(this, frameBuffer);
    _graphics = new AndroidGraphics(getAssets(), frameBuffer);
    _fileIO = new AndroidFileIO(this);
    _audio = new AndroidAudio(this);
    _input = new AndroidInput(this, _renderView, scaleX, scaleY);
    _screen = getStartScreen();
    setContentView(_renderView);

    PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
    _wakelock = powerManager.newWakeLock(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, "GlGame");
  }

  @Override
  protected void onResume() {
    super.onResume();
    _wakelock.acquire();
    _screen.resume();
    _renderView.resume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    _wakelock.release();
    _renderView.pause();
    _screen.pause();

    if (isFinishing()) {
      _screen.dispose();
    }
  }

  @Override
  public Input getInput() {
    return _input;
  }

  @Override
  public FileIO getFileIO() {
    return _fileIO;
  }

  @Override
  public Graphics getGraphics() {
    return _graphics;
  }

  @Override
  public Audio getAudio() {
    return _audio;
  }

  @Override
  public void setScreen(@NonNull Screen screen) {
    if (screen == null) {
      throw new RuntimeException("Screen must not be null");
    }
    _screen.pause();
    _screen.dispose();
    screen.resume();
    screen.update(0);
    _screen = screen;
  }

  @Override
  public Screen getCurrentScreen() {
    return _screen;
  }

  @Override
  public Screen getStartScreen() {
    return null;
  }
}
