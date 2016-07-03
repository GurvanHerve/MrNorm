package com.navrug.game.mrnorm;

import android.graphics.Color;
import android.util.Log;

import com.navrug.game.interfaces.Game;
import com.navrug.game.interfaces.Graphics;
import com.navrug.game.interfaces.Input;
import com.navrug.game.interfaces.Pixmap;
import com.navrug.game.interfaces.Screen;
import com.navrug.game.model.Snake;
import com.navrug.game.model.SnakePart;
import com.navrug.game.model.Stain;
import com.navrug.game.model.World;
import com.navrug.game.utils.Assets;

import java.util.List;

import static com.navrug.game.mrnorm.GameScreen.GameState.Running;


public class GameScreen extends Screen {
  enum GameState {
    Ready,
    Running,
    Paused,
    GameOver
  }

  private GameState _state = GameState.Ready;
  private World _world;
  private int _oldScore = 0;
  private String _score = "";

  public GameScreen(Game game) {
    super(game);
    _world = new World();
  }

  @Override
  public void update(float deltaTime) throws IllegalAccessException {
    List<Input.TouchEvent> events = game.getInput().getTouchEvents();
    game.getInput().getKeyEvents();

    switch (_state) {
      case Ready:
        updateReady(events);
        break;
      case Running:
        updateRunning(events, (int) deltaTime);
        break;
      case Paused:
        updatePaused(events);
        break;
      case GameOver:
        updateGameOver(events);
        break;
      default:
        Log.e("GameScreen", "Unexpected case");
    }
  }

  private void updateReady(List<Input.TouchEvent> events) {
    if (events.size() > 0) {
      _state = Running;
    }
  }

  private void updateRunning(List<Input.TouchEvent> events, int deltaTime) {
    for (Input.TouchEvent event : events) {
      if (event._type == Input.TouchEvent.TOUCH_UP) {
        if (event._x < 64 && event._y < 64) {
          if (Settings._soundEnabled) {
            Assets._click.play(1);
          }
          _state = GameState.Paused;
          return;
        }
      }
      if (event._type == Input.TouchEvent.TOUCH_DOWN) {
        if (event._x < 64 && event._y > 416) {
          _world._snake.turnLeft();
        }
        if (event._x > 256 && event._y > 416) {
          _world._snake.turnRight();
        }
      }
      _world.update(deltaTime);
      if (_world._gameOver) {
        if (Settings._soundEnabled) {
          Assets._bitten.play(1);
        }
        _state = GameState.GameOver;
      }
      if (_oldScore != _world._score) {
        _oldScore = _world._score;
        _score = "" + _oldScore;
        if (Settings._soundEnabled) {
          Assets._eat.play(1);
        }
      }
    }
  }

  private void updatePaused(List<Input.TouchEvent> events) throws IllegalAccessException {
    for (Input.TouchEvent event : events) {
      if (event._type == Input.TouchEvent.TOUCH_UP) {
        if (event._x > 80 && event._x <= 240) {
          if (event._y > 100 && event._y <= 148) {
            if (Settings._soundEnabled) {
              Assets._click.play(1);
            }
            _state = Running;
          }
          if (event._y > 148 && event._y < 196) {
            if (Settings._soundEnabled) {
              Assets._click.play(1);
            }
            game.setScreen(new MainMenuScreen(game));
            return;
          }
        }
      }
    }
  }

  private void updateGameOver(List<Input.TouchEvent> events) throws IllegalAccessException {
    for (Input.TouchEvent event : events) {
      if (event._type == Input.TouchEvent.TOUCH_UP) {
        if (event._x >= 128 && event._x <= 192 && event._y >= 200 && event._y <= 264) {
          if (Settings._soundEnabled) {
            Assets._click.play(1);
          }
          game.setScreen(new MainMenuScreen(game));
        }
      }
    }
  }

  @Override
  public void present(float deltaTime) {
    Graphics g = game.getGraphics();
    g.drawPixmap(Assets._background, 0, 0);
    drawWorld(_world);
    switch (_state) {
      case Ready:
        drawReadyUI();
        break;
      case Running:
        drawRunningUI();
        break;
      case Paused:
        drawPausedUI();
        break;
      case GameOver:
        drawGameOverUI();
        break;
      default:
        Log.e("GameScreen", "Unexpected case");
        break;
    }
    drawText(g, _score, g.getWidth() / 2 - _score.length() * 20 / 2, g.getHeight() - 42);
  }

  private void drawWorld(World world) {
    Graphics g = game.getGraphics();
    Snake snake = world._snake;
    SnakePart head = snake._parts.get(0);
    Stain stain = world._stain;

    Pixmap stainPixmap = null;
    switch (stain._type) {
      case Stain.TYPE_1:
        stainPixmap = Assets._stain1;
        break;
      case Stain.TYPE_2:
        stainPixmap = Assets._stain2;
        break;
      case Stain.TYPE_3:
        stainPixmap = Assets._stain3;
        break;
      default:
        Log.e("GameScreen", "Unexpected case");
        break;
    }
    int x = stain._x * 32;
    int y = stain._y * 32;
    g.drawPixmap(stainPixmap, x, y);

    for (SnakePart part : snake._parts) {
      x = part._x * 32;
      y = part._y * 32;
      g.drawPixmap(Assets._tail, x, y);
    }

    Pixmap headPixmap = null;
    switch (snake._direction) {
      case Snake.UP:
        headPixmap = Assets._headUp;
        break;
      case Snake.LEFT:
        headPixmap = Assets._headLeft;
        break;
      case Snake.DOWN:
        headPixmap = Assets._headDown;
        break;
      case Snake.RIGHT:
        headPixmap = Assets._headRight;
        break;
      default:
        Log.e("GameScreen", "Unexpecdted case");
    }
    x = head._x * 32 + 16;
    y = head._y * 32 + 16;
    g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
  }

  private void drawReadyUI() {
    Graphics g = game.getGraphics();
    g.drawPixmap(Assets._ready, 47, 100);
    g.drawLine(0, 416, 480, 416, Color.BLACK);
  }

  private void drawRunningUI() {
    Graphics g = game.getGraphics();
    g.drawPixmap(Assets._buttons, 0, 0, 64, 128, 64, 64);
    g.drawLine(0, 416, 480, 416, Color.BLACK);
    g.drawPixmap(Assets._buttons, 0, 416, 64, 64, 64, 64);
    g.drawPixmap(Assets._buttons, 256, 416, 0, 64, 64, 64);
  }

  private void drawPausedUI() {
    Graphics g = game.getGraphics();
    g.drawPixmap(Assets._pause, 80, 100);
    g.drawLine(0, 416, 480, 416, Color.BLACK);
  }

  private void drawGameOverUI() {
    Graphics g = game.getGraphics();
    g.drawPixmap(Assets._gameOver, 62, 100);
    g.drawPixmap(Assets._buttons, 128, 200, 0, 128, 64, 64);
    g.drawLine(0, 416, 480, 416, Color.BLACK);
  }

  private void drawText(Graphics graphics, String line, int x, int y) {
    int len = line.length();
    for (int i = 0; i < len; i++) {
      char ch = line.charAt(i);

      if (ch == ' ') {
        x += 20;
        continue;
      }

      int srcX = 0;
      int srcWidth = 0;
      if (ch == '.') {
        srcX = 200;
        srcWidth = 10;
      } else {
        srcX = (ch - '0') * 20;
        srcWidth = 20;
      }

      graphics.drawPixmap(Assets._numbers, x, y, srcX, 0, srcWidth, 32);
      x += srcWidth;
    }
  }

  @Override
  public void pause() {
    if (_state == Running) {
      _state = GameState.Paused;
    }
    if (_world._gameOver) {
      Settings.addScore(_world._score);
      Settings.save(game.getFileIO());
    }
  }

  @Override
  public void resume() {

  }

  @Override
  public void dispose() {

  }
}
