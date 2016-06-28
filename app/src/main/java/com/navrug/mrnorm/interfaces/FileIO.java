package com.navrug.mrnorm.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

interface FileIO {
  InputStream readAsset(String fileName) throws IOException;
  InputStream readFile(String fileName) throws IOException;
  OutputStream writeFile(String fileName) throws IOException;
}
