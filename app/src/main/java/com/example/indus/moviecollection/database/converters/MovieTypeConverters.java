package com.example.indus.moviecollection.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.example.indus.moviecollection.model.GenresItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MovieTypeConverters {

    @TypeConverter
    public static List<GenresItem> stringToGenres(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GenresItem>>() {}.getType();
        List<GenresItem> genres = gson.fromJson(json, type);
        return genres;
    }

    @TypeConverter
    public static String genresToString(List<GenresItem> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GenresItem>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
