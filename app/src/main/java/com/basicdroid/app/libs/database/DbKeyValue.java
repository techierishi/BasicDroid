package com.basicdroid.app.libs.database;

/**
 * Created by dina on 3/12/15.
 */
public class DbKeyValue {

    private long id = 0;
    private String key = "", value = "";

    public DbKeyValue(long id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
