package com.navrug.game.interfaces;

public interface Pixmap {
  int getWidth();
  int getHeight();
  Graphics.PixmapFormat getFormat();
  void dispose();
}
