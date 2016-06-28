package com.navrug.mrnorm.impl;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.navrug.mrnorm.interfaces.Graphics;
import com.navrug.mrnorm.interfaces.Pixmap;

import java.io.IOException;
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
    this._canvas = new Canvas(frameBuffer);
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
    Bitmap bitmap = null;
    try {
      is = _assets.open(fileName);
      bitmap = BitmapFactory.decodeStream(is);
      if (bitmap == null) {
        throw new RuntimeException("Couldn't load bitmap from asset'" + fileName + "'");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    if (bitmap.getConfig() == Bitmap.Config.RGB_565) {
      format = PixmapFormat.RGB565;
    } else if (bitmap.getConfig() == Bitmap.Config.ARGB_4444) {
      format = PixmapFormat.ARGB4444;
    } else {
      format = PixmapFormat.ARGB8888;
    }

    return new AndroidPixmap(bitmap, format);
  }

  @Override
  public void clear(int color) {
    _canvas.drawRGB((color & 0XFF0000) >> 16, (color & 0XFF00) >> 8, (color & 0XFF));
  }

  @Override
  public void drawPixel(int x, int y, int color) {
    _paint.setColor(color);
    _canvas.drawPoint(x, y, _paint);
  }

  @Override
  public void drawLine(int x, int y, int x2, int y2, int color) {
    _paint.setColor(color);
    _canvas.drawLine(x, y, x2, y2, _paint);
  }

  @Override
  public void drawRect(int x, int y, int width, int height, int color) {
    _paint.setColor(color);
    _paint.setStyle(Paint.Style.FILL);
    _canvas.drawRect(x, y, width, height, _paint);
  }

  @Override
  public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {
    _srcRect.left = srcX;
    _srcRect.top = srcY;
    _srcRect.right = srcX + srcWidth - 1;
    _srcRect.bottom = srcY + srcHeight - 1;

    _dstRect.left = x;
    _dstRect.top = y;
    _dstRect.right = x + srcWidth - 1;
    _dstRect.bottom = y + srcHeight - 1;

    _canvas.drawBitmap(((AndroidPixmap) pixmap)._bitmap, _srcRect, _dstRect, null);
  }

  @Override
  public void drawPixmap(Pixmap pixmap, int x, int y) {
    _canvas.drawBitmap(((AndroidPixmap) pixmap)._bitmap, x, y, null);
  }

  @Override
  public int getWidth() {
    return _frameBuffer.getWidth();
  }

  @Override
  public int getHeight() {
    return _frameBuffer.getHeight();
  }
}
