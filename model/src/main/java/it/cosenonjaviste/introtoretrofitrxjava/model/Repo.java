package it.cosenonjaviste.introtoretrofitrxjava.model;

public class Repo {
    private long id;

    private String name;

    private User owner;

    public Repo() {
    }

    public Repo(long id, String name, User owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    @Override public String toString() {
        return owner + " " + name;
    }
}
