package com.navrug.mrnorm.impl;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.navrug.mrnorm.interfaces.Graphics;
import com.navrug.mrnorm.interfaces.Pixmap;

import java.io.InputStream;


public class AndroidGraphics implements Graphics {
  private AssetManager _assets;
  private Bitmap _frameBuffer;
  private Canvas _canvas;
  private Paint _paint;
  private Rect _srcRect = new Rect();
  private Rect _dstRect = new Rect();

  public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
    this._assets = assets;
    this._frameBuffer = frameBuffer;
    this._canvas = new Canvas();
    this._paint = new Paint();
  }

  @Override
  public Pixmap newPixmap(String fileName, PixmapFormat format) {
    Bitmap.Config config = null;
    if (format == PixmapFormat.RGB565) {
      config = Bitmap.Config.RGB_565;
    } else if (format == PixmapFormat.ARGB4444) {
      config = Bitmap.Config.ARGB_8888;
    }

    BitmapFactory.Options opt = new BitmapFactory.Options();
    opt.inPreferredConfig = config;

    InputStream is = null;

    return null;
  }

  @Override
  public void clear(int color) {

  }

  @Override
  public void drawPixel(int x, int y, int color) {

  }

  @Override
  public void drawLine(int x, int y, int x2, int y2, int color) {

  }

  @Override
  public void drawRect(int x, int y, int width, int height, int color) {

  }

  @Override
  public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {

  }

  @Override
  public void drawPixmap(Pixmap pixmap, int x, int y) {

  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }
}
