package com.navrug.mrnorm.impl;

import android.graphics.Bitmap;

import com.navrug.mrnorm.interfaces.Graphics;
import com.navrug.mrnorm.interfaces.Pixmap;


public class AndroidPixmap implements Pixmap {
  Bitmap _bitmap;
  private Graphics.PixmapFormat _format;

  public AndroidPixmap(Bitmap bitmap, Graphics.PixmapFormat format) {
    this._bitmap = bitmap;
    this._format = format;
  }

  @Override
  public int getWidth() {
    return _bitmap.getWidth();
  }

  @Override
  public int getHeight() {
    return _bitmap.getHeight();
  }

  @Override
  public Graphics.PixmapFormat getFormat() {
    return _format;
  }

  @Override
  public void dispose() {
    _bitmap.recycle();
  }
}
