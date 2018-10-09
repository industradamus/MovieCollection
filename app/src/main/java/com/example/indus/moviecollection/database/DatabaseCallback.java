package com.example.indus.moviecollection.database;

public interface DatabaseCallback<D> {

    void onDataLoaded(D data);
    void onActionSuccess();
}
