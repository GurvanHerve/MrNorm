package com.navrug.game.mrnorm;

import com.navrug.game.interfaces.Game;
import com.navrug.game.interfaces.Graphics;
import com.navrug.game.interfaces.Screen;
import com.navrug.game.utils.Assets;


public class LoadginScreen extends Screen {

  public LoadginScreen(Game game) {
    super(game);
  }

  @Override
  public void update(float deltaTime) {
    Graphics g = game.getGraphics();
    Assets._background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
    Assets._logo = g.newPixmap("logo.png", Graphics.PixmapFormat.ARGB4444);
    Assets._mainMenu = g.newPixmap("mainmenu.png", Graphics.PixmapFormat.ARGB4444);
    Assets._buttons = g.newPixmap("buttons.png", Graphics.PixmapFormat.ARGB4444);
    Assets._help1 = g.newPixmap("help1.png", Graphics.PixmapFormat.ARGB4444);
    Assets._help2 = g.newPixmap("help2.png", Graphics.PixmapFormat.ARGB4444);
    Assets._help3 = g.newPixmap("help3.png", Graphics.PixmapFormat.ARGB4444);
    Assets._numbers = g.newPixmap("numbers.png", Graphics.PixmapFormat.ARGB4444);
    Assets._ready = g.newPixmap("ready.png", Graphics.PixmapFormat.ARGB4444);
    Assets._pause = g.newPixmap("pausemenu.png", Graphics.PixmapFormat.ARGB4444);
    Assets._gameOver = g.newPixmap("gameover.png", Graphics.PixmapFormat.ARGB4444);
    Assets._headUp = g.newPixmap("headup.png", Graphics.PixmapFormat.ARGB4444);
    Assets._headLeft = g.newPixmap("headleft.png", Graphics.PixmapFormat.ARGB4444);
    Assets._headDown = g.newPixmap("headdown.png", Graphics.PixmapFormat.ARGB4444);
    Assets._headRight = g.newPixmap("headright.png", Graphics.PixmapFormat.ARGB4444);
    Assets._tail = g.newPixmap("tail.png", Graphics.PixmapFormat.ARGB4444);
    Assets._stain1 = g.newPixmap("stain1.png", Graphics.PixmapFormat.ARGB4444);
    Assets._stain2 = g.newPixmap("stain2.png", Graphics.PixmapFormat.ARGB4444);
    Assets._stain3 = g.newPixmap("stain3.png", Graphics.PixmapFormat.ARGB4444);
    Assets._click = game.getAudio().newSound("click.ogg");
    Assets._eat = game.getAudio().newSound("eat.ogg");
    Assets._bitten = game.getAudio().newSound("bitten.ogg");
    Settings.load(game.getFileIO());
    game.setScreen(new MainMenuScreen(game));
  }

  @Override
  public void present(float deltaTime) {

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
