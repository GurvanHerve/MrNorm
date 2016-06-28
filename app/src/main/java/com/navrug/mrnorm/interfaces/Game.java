package com.navrug.mrnorm.interfaces;

interface Game {
  Input getInput();
  FileIO getFileIO();
  Graphics getGraphics();
  Audio getAudio();
  void setScreen(Screen screen);
  Screen getCurrentScreen();
  Screen getStartScreen();
}
