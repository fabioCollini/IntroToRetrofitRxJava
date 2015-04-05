package it.cosenonjaviste.introtoretrofitrxjava.model;

import java.util.List;
import java.util.Set;

public class RepoStats {
    private String repoName;

    private List<User> contributors;

    private Set<String> languages;

    public RepoStats(String repoName, List<User> contributors, Set<String> languages) {
        this.repoName = repoName;
        this.contributors = contributors;
        this.languages = languages;
    }

    @Override public String toString() {
        return repoName + "\n\t" + contributors + "\n\t" + languages;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepoStats repoStats = (RepoStats) o;

        return repoName.equals(repoStats.repoName);

    }

    @Override public int hashCode() {
        return repoName.hashCode();
    }
}
