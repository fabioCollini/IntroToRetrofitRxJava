package it.cosenonjaviste.introtoretrofitrxjava.model;

import com.google.gson.annotations.SerializedName;

import java.text.MessageFormat;

public class Tag {

    @SerializedName("tag_name")
    private String name;

    @SerializedName("answer_count")
    private int count;

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    @Override public String toString() {
        return MessageFormat.format("{0} ({1})", name, count);
    }
}
