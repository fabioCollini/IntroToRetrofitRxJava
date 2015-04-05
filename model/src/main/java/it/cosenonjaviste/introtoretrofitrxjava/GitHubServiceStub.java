package it.cosenonjaviste.introtoretrofitrxjava;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import it.cosenonjaviste.introtoretrofitrxjava.model.Repo;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import retrofit.http.Path;
import rx.Observable;

public class GitHubServiceStub implements GitHubService {
    @Override public Observable<RepoResponse> listLastRepos() {
        return Observable.just(new RepoResponse(createRepo(new Random().nextInt(3)), createRepo(2), createRepo(3))).delay(random(), TimeUnit.MILLISECONDS);
    }

    private int random() {
        return 100 + new Random().nextInt(100);
    }

    private Repo createRepo(int id) {
        return new Repo(id, "repo " + id, new User(id, "user " + id));
    }

    @Override public Observable<List<User>> listContributors(@Path("owner") String ownerLoginName, @Path("repo") String repoName) {
        return Observable.just(new User(1, "user 1")).toList().delay(random(), TimeUnit.MILLISECONDS);
    }

    @Override public Observable<Map<String, String>> listLanguages(@Path("owner") String ownerLoginName, @Path("repo") String repoName) {
        return Observable.just(Collections.singletonMap("java", "aaaa")).delay(random(), TimeUnit.MILLISECONDS);
    }
}
