package com.navrug.game.model;

public class Stain {
  public static final int TYPE_1 = 0;
  public static final int TYPE_2 = 1;
  public static final int TYPE_3 = 2;

  int _x;
  int _y;
  private int _type;

  public Stain(int x, int y, int type) {
    this._x = x;
    this._y = y;
    this._type = type;
  }
}
