package com.navrug.game.mrnorm;

import com.navrug.game.interfaces.Game;
import com.navrug.game.interfaces.Graphics;
import com.navrug.game.interfaces.Input;
import com.navrug.game.interfaces.Screen;
import com.navrug.game.utils.Assets;

import java.util.List;


public class HighscoreScreen extends Screen {
  private String[] _highscores;

  public HighscoreScreen(Game game) {
    super(game);
    for (int i = 0; i < 5; i++) {
      _highscores[i] = "" + (i+1) + ". " + Settings._highscores[i];
    }
  }

  @Override
  public void update(float deltaTime) {
    List<Input.TouchEvent> events = game.getInput().getTouchEvents();
    game.getInput().getKeyEvents();

    for (Input.TouchEvent event : events) {
      if (event._type == Input.TouchEvent.TOUCH_UP) {
        if (event._x < 64 && event._y > 416) {
          if (Settings._soundEnabled) {
            Assets._click.play(1);
            game.setScreen(new MainMenuScreen(game));
            return;
          }
        }
      }
    }
  }

  @Override
  public void present(float deltaTime) {
    Graphics g = game.getGraphics();
    g.drawPixmap(Assets._background, 0, 0);
    g.drawPixmap(Assets._mainMenu, 64, 20, 0, 42, 196, 42);
    int y = 100;
    for (int i = 0; i < 5; i++) {
      drawText(g, _highscores[i], 20, y);
      y += 50;
    }
    g.drawPixmap(Assets._buttons, 0, 416, 64, 64, 64, 64);
  }

  @Override
  public void pause() {


  }
  @Override
  public void resume() {

  }

  @Override
  public void dispose() {

  }

  private static void drawText(Graphics graphics, String line, int x, int y) {
    int length = line.length();
    for (int i = 0; i < length; i++) {
      char ch = line.charAt(i);
      if (ch == ' ') {
        x += 20;
        continue;
      }

      int srcX;
      int srcWidth;
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
}
