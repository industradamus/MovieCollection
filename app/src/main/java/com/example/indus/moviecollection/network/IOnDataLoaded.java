package com.example.indus.moviecollection.network;

public interface IOnDataLoaded<T> {

    void onNewDataLoaded(T data);
    void onErrorDataLoaded();
}
