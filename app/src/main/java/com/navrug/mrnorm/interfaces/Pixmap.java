package com.navrug.mrnorm.interfaces;

public interface Pixmap {
  int getWidth();
  int getHeight();
  Graphics.PixmapFormat getFormat();
  void dispose();
}
