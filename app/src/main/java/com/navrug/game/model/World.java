package com.navrug.game.model;

import java.util.Random;

public class World {
  private static final int WORLD_WIDTH = 10;
  private static final int WORLD_HEIGHT = 13;
  private static final int SCORE_INCREMENT = 10;
  private static final float TICK_INITIAL = 0.5F;
  private static final float TICK_DECREMENT = 0.05F;

  public Snake _snake;
  public Stain _stain;
  public boolean _gameOver = false;
  public int _score = 0;

  private boolean _fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
  private Random _random;
  private float _tickTime = 0;
  private float _tick = TICK_INITIAL;

  public World() {
    _snake = new Snake();
    placeStain();
  }

  private void placeStain() {
    for (int x = 0; x < WORLD_WIDTH; x++) {
      for (int y = 0; x < WORLD_HEIGHT; y++) {
        _fields[x][y] = false;
      }
    }

    int len = _snake._parts.size();
    for (int i = 0; i < len; i++) {
      SnakePart part = _snake._parts.get(i);
      _fields[part._x][part._y] = true;
    }

    int stainX = _random.nextInt(WORLD_WIDTH);
    int stainY = _random.nextInt(WORLD_HEIGHT);
    while (true) {
      if (!_fields[stainX][stainY]) {
        break;
      }
      stainX += 1;
      if (stainX >= WORLD_WIDTH) {
        stainX = 0;
        stainY += 1;
        if (stainY >= WORLD_HEIGHT) {
          stainY = 0;
        }
      }
    }
    _stain = new Stain(stainX, stainY, _random.nextInt(3));
  }

  public void update(int deltatime) {
    if (_gameOver) {
      return;
    }

    _tickTime += deltatime;

    while (_tickTime > _tick) {
      _tickTime -= _tick;
      _snake.advance();
      if (_snake.checkBitten()) {
        _gameOver = true;
        return;
      }

      SnakePart head = _snake._parts.get(0);
      if (head._x == _stain._x && head._y == _stain._y) {
        _score += SCORE_INCREMENT;
        _snake.eat();
        if (_snake._parts.size() == WORLD_HEIGHT * WORLD_WIDTH) {
          _gameOver = true;
          return;
        } else {
          placeStain();
        }

        if (_score % 100 == 0 && _tick - TICK_DECREMENT > 0) {
          _tick -= TICK_DECREMENT;
        }
      }
    }
  }
}
