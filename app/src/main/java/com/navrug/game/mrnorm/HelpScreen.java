package com.navrug.game.mrnorm;

import com.navrug.game.interfaces.Game;
import com.navrug.game.interfaces.Graphics;
import com.navrug.game.interfaces.Input;
import com.navrug.game.interfaces.Screen;
import com.navrug.game.utils.Assets;

import java.util.List;
import java.util.Set;


public class HelpScreen extends Screen {
  public HelpScreen(Game game) {
    super(game);
  }

  @Override
  public void update(float deltaTime) {
    List<Input.TouchEvent> events = game.getInput().getTouchEvents();
    game.getInput().getKeyEvents();

    for (Input.TouchEvent event : events) {
      if (event._type == Input.TouchEvent.TOUCH_UP) {
        if (event._x > 256 && event._y > 416) {
          game.setScreen(new HelpScreen2(game));
          if (Settings._soundEnabled) {
            Assets._click.play(1);
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
    g.drawPixmap(Assets._help1, 64, 100);
    g.drawPixmap(Assets._buttons, 256, 416, 0, 64, 64, 64);
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
}
