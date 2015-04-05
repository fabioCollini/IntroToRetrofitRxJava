package it.cosenonjaviste.introtoretrofitrxjava.model;

import java.util.Arrays;
import java.util.List;

public class RepoResponse {
    private List<Repo> items;

    public RepoResponse() {
    }

    public RepoResponse(Repo... items) {
        this.items = Arrays.asList(items);
    }

    public List<Repo> getItems() {
        return items;
    }
}
