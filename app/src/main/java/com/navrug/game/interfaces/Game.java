package com.navrug.game.interfaces;

public interface Game {
  Input getInput();
  FileIO getFileIO();
  Graphics getGraphics();
  Audio getAudio();
  void setScreen(Screen screen) throws IllegalAccessException;
  Screen getCurrentScreen();
  Screen getStartScreen();
}
