package com.kamged.kmovies.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_year() {
        return _year;
    }

    public void set_year(String _year) {
        this._year = _year;
    }

    public int getUid() {
        return _uid;
    }

    public void setUid(int uid) {
        this._uid = uid;
    }

    @PrimaryKey(autoGenerate = true)
    private int _uid;

    @ColumnInfo(name = "year")
    private String _year;

    @ColumnInfo(name = "title")
    private String _title;

    public String get_runtime() {
        return _runtime;
    }

    public void set_runtime(String _runtime) {
        this._runtime = _runtime;
    }

    @ColumnInfo(name = "runtime")
    private String _runtime;

    public String get_coverUrl() {
        return _coverUrl;
    }

    public void set_coverUrl(String _coverUrl) {
        this._coverUrl = _coverUrl;
    }

    @ColumnInfo(name = "cover_url")
    private String _coverUrl;
}
