package it.cosenonjaviste.introtoretrofitrxjava;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoStats;
import rx.Observable;

public class TopRepoLoader {

    private GitHubService service;

    public TopRepoLoader(GitHubService service) {
        this.service = service;
    }

    public Observable<RepoStats> poll() {
        return Observable.interval(10000, TimeUnit.MILLISECONDS).switchMap(l -> load(1)).distinctUntilChanged();
    }

    public Observable<RepoStats> load(int limit) {
        return service.listLastRepos()
                .flatMapIterable(RepoResponse::getItems)
                .limit(limit)
                .flatMap(repo -> loadRepoStats(repo.getOwner().getLogin(), repo.getName()));
    }

    private Observable<RepoStats> loadRepoStats(String login, String repoName) {
        return Observable.zip(
                service.listContributors(login, repoName),
                service.listLanguages(login, repoName).map(Map::keySet),
                (contributors, languages) -> new RepoStats(repoName, contributors, languages)
        );
    }
}
