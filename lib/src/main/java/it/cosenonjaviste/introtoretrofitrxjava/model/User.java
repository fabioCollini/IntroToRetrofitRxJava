package it.cosenonjaviste.introtoretrofitrxjava.model;

import com.google.gson.annotations.SerializedName;

import java.text.MessageFormat;

public class User {

    @SerializedName("user_id")
    private int id;

    private int reputation;

    @SerializedName("display_name")
    private String name;

    private String location;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getReputation() {
        return reputation;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override public String toString() {
        if (location == null) {
            return MessageFormat.format("{0} {1}", reputation, name);
        } else {
            return MessageFormat.format("{0} {1} ({2})", reputation, name, location);
        }
    }
}
