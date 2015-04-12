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

    @SerializedName("profile_image")
    private String image;

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

    public String getImage() {
        return image;
    }

    @Override public String toString() {
        if (location == null) {
            return MessageFormat.format("<b>{0}</b> {1}", name, reputation);
        } else {
            return MessageFormat.format("<b>{0}</b> {1}<br/>{2}", name, reputation, location);
        }
    }
}
