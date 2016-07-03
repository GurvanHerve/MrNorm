package com.navrug.game.mrnorm;

import com.navrug.game.impl.AndroidGame;
import com.navrug.game.interfaces.Screen;


public class MrNorm extends AndroidGame {

  public Screen getStartScreen() {
    return new LoadginScreen(this);
  }
}
