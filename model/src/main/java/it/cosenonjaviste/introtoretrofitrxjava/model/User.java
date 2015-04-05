package it.cosenonjaviste.introtoretrofitrxjava.model;

public class User {
    private long id;

    private String login;

    public User() {
    }

    public User(long id, String login) {
        this.id = id;
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    @Override public String toString() {
        return login;
    }
}
