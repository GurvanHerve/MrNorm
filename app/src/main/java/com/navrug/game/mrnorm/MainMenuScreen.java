package com.navrug.game.mrnorm;

import com.navrug.game.interfaces.Game;
import com.navrug.game.interfaces.Graphics;
import com.navrug.game.interfaces.Input;
import com.navrug.game.interfaces.Screen;
import com.navrug.game.utils.Assets;

import java.util.List;


public class MainMenuScreen extends Screen {

  public MainMenuScreen(Game game) {
    super(game);
  }

  @Override
  public void update(float deltaTime) throws IllegalAccessException {
    Graphics g = game.getGraphics();
    List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
    game.getInput().getKeyEvents();
    for (Input.TouchEvent event : touchEvents) {
      if (event._type == Input.TouchEvent.TOUCH_UP) {
        if (inBounds(event, 0, g.getHeight() - 64, 64, 64)) {
          Settings._soundEnabled = !Settings._soundEnabled;
          if (Settings._soundEnabled) {
            Assets._click.play(1);
            return;
          }
          if (inBounds(event, 64, 220, 192, 42)) {
            game.setScreen(new GameScreen(game));
            if (Settings._soundEnabled) {
              Assets._click.play(1);
              return;
            }
          }
          if (inBounds(event, 64, 220 + 42, 192, 42)) {
            game.setScreen(new HighscoreScreen(game));
            if (Settings._soundEnabled) {
              Assets._click.play(1);
              return;
            }
          }
          if (inBounds(event, 64, 220 + 42, 192, 42)) {
            game.setScreen(new HelpScreen(game));
            if (Settings._soundEnabled) {
              Assets._click.play(1);
              return;
            }
          }
        }
      }
    }
  }

  private static boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height) {
    return (event._x > x && event._x < x + width - 1) && (event._y > y && event._y < y + height - 1);
  }

  @Override
  public void present(float deltaTime) {
    Graphics g = game.getGraphics();
    g.drawPixmap(Assets._background, 0, 0);
    g.drawPixmap(Assets._logo, 32, 20);
    g.drawPixmap(Assets._mainMenu, 64, 220);
    if (Settings._soundEnabled) {
      g.drawPixmap(Assets._buttons, 0, 416, 0, 0, 64, 64);
    } else {
      g.drawPixmap(Assets._buttons, 0, 416, 64, 0, 64, 64);
    }
  }

  @Override
  public void pause() {
    Settings.save(game.getFileIO());
  }

  @Override
  public void resume() {

  }

  @Override
  public void dispose() {

  }
}
