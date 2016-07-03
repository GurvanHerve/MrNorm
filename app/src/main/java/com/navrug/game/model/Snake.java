package com.navrug.game.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Snake {
  public final static int UP = 0;
  public final static int LEFT = 1;
  public final static int DOWN = 2;
  public final static int RIGHT = 3;

  public List<SnakePart> _parts = new ArrayList<>();
  public int _direction;

  public Snake() {
    _direction = UP;
    _parts.add(new SnakePart(5, 6));
    _parts.add(new SnakePart(5, 7));
    _parts.add(new SnakePart(5, 8));
  }

  public void turnLeft() {
    _direction += 1;
    if (_direction > RIGHT) {
      _direction = UP;
    }
  }

  public void turnRight() {
    _direction -= 1;
    if (_direction < UP) {
      _direction = RIGHT;
    }
  }

  public void eat() {
    SnakePart end = _parts.get(_parts.size() - 1);
    _parts.add(new SnakePart(end._x, end._y));
  }

  public void advance() {
    SnakePart head = _parts.get(0);
    int len = _parts.size() - 1;
    for (int i = len; i > 0; i--) {
      SnakePart before = _parts.get(i - 1);
      SnakePart part = _parts.get(i);
      part._x = before._x;
      part._y = before._y;
    }

    switch (_direction) {
      case UP:
        head._y -= 1;
        break;
      case LEFT:
        head._x -= 1;
        break;
      case DOWN:
        head._y += 1;
        break;
      case RIGHT:
        head._x += 1;
        break;
      default:
        Log.d("SNAKE DIRECTION", "Unexpected direction");
    }

    if (head._x < 0) {
      head._x = 9;
    }
    if (head._x > 9) {
      head._x = 0;
    }
    if (head._y < 0) {
      head._y = 12;
    }
    if (head._y > 12) {
      head._y = 0;
    }
  }

  public boolean checkBitten() {
    SnakePart head = _parts.get(0);
    for (SnakePart part : _parts) {
      if (part._x == head._x && part._y == head._y) {
        return true;
      }
    }
    return false;
  }
}
