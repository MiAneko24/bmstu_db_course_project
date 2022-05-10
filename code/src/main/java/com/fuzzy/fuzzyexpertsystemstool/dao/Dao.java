package com.fuzzy.fuzzyexpertsystemstool.dao;

import java.util.List;

public interface Dao<T> {
    T get(int id);

    List<T> getAll(int id);

    void save(T t);

    void update(T t);

    void delete(T t);
}
