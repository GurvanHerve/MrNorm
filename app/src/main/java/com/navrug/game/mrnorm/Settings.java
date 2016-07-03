package com.navrug.game.mrnorm;

import com.navrug.game.interfaces.FileIO;
import com.navrug.game.interfaces.Input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Settings {
  public static boolean _soundEnabled = true;
  public static int[] _highscores = new int[]{100, 80, 50, 30, 10};

  public static void load(FileIO files) {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new InputStreamReader(files.readFile(".mrnorm")));
      _soundEnabled = Boolean.parseBoolean(br.readLine());
      for (int i = 0; i < 5; i++) {
        _highscores[i] = Integer.parseInt(br.readLine());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void save(FileIO files) {
    BufferedWriter bw = null;
    try {
      bw = new BufferedWriter(new OutputStreamWriter(files.writeFile(".mrnorm")));
      bw.write(Boolean.toString(_soundEnabled));
      for (int score : _highscores) {
        bw.write(Integer.toString(score));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bw != null) {
        try {
          bw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void addScore(int score) {
    for (int i = 0; i < 5; i++) {
      if (_highscores[i] < score) {
        for (int j = 4; j > i; j--) {
          _highscores[j] = _highscores[j - 1];
        }
        _highscores[i] = score;
        break;
      }
    }
  }
}
