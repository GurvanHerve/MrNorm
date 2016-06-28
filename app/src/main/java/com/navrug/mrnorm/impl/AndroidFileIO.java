package com.navrug.mrnorm.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.navrug.mrnorm.interfaces.FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AndroidFileIO implements FileIO {
  private Context _context;
  private AssetManager _assets;
  private String _externalStorage;

  public AndroidFileIO(Context context) {
    _context = context;
    _assets = context.getAssets();
    _externalStorage = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
  }

  @Override
  public InputStream readAsset(String fileName) throws IOException {
    return _assets.open(fileName);
  }

  @Override
  public InputStream readFile(String fileName) throws IOException {
    return new FileInputStream(_externalStorage + fileName);
  }

  @Override
  public OutputStream writeFile(String fileName) throws IOException {
    return new FileOutputStream(_externalStorage + fileName);
  }

  public SharedPreferences getSharedPreferences() {
    return PreferenceManager.getDefaultSharedPreferences(_context);
  }
}
