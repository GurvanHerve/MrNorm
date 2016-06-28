package com.navrug.mrnorm;

import com.navrug.mrnorm.impl.AndroidGame;
import com.navrug.mrnorm.interfaces.Screen;


public class MrNorm extends AndroidGame {

  public Screen getStartScreen() {
    return new MainMenu(this);
  }
}
