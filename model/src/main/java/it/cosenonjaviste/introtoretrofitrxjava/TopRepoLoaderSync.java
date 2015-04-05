package it.cosenonjaviste.introtoretrofitrxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.cosenonjaviste.introtoretrofitrxjava.model.Repo;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoStats;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;

public class TopRepoLoaderSync {

    private GitHubServiceSync service;

    public TopRepoLoaderSync(GitHubServiceSync service) {
        this.service = service;
    }

    public List<RepoStats> load(int limit) {
        RepoResponse repoResponse = service.listLastRepos();
        List<Repo> items = repoResponse.getItems();
        List<RepoStats> statsList = new ArrayList<>();
        for (Repo repo : items) {
            statsList.add(loadRepoStats(repo.getOwner().getLogin(), repo.getName()));
        }
        return statsList;
    }

    private RepoStats loadRepoStats(String login, String repoName) {
        List<User> contributors = service.listContributors(login, repoName);
        Set<String> languages = service.listLanguages(login, repoName).keySet();
        return new RepoStats(repoName, contributors, languages);
    }
}
