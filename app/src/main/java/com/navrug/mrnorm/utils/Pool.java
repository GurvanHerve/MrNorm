package com.navrug.mrnorm.utils;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {
  public interface PoolObjectFactory<T> {
    T createObject();
  }

  private final List<T> _freeObjects;
  private final PoolObjectFactory<T> _factory;
  private final int _maxSize;

  public Pool(PoolObjectFactory<T> factory, int maxSize) {
    this._factory = factory;
    this._maxSize = maxSize;
    this._freeObjects = new ArrayList<T>(maxSize);
  }

  public T newObject() {
    T obj = null;

    if (_freeObjects.isEmpty()) {
      obj = _factory.createObject();
    } else {
      obj = _freeObjects.remove(_freeObjects.size() - 1);
    }

    return obj;
  }

  public void free(T obj) {
    if (_freeObjects.size() < _maxSize) {
      _freeObjects.add(obj);
    }
  }
}
