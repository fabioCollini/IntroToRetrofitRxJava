package it.cosenonjaviste.introtoretrofitrxjava;

import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import it.cosenonjaviste.introtoretrofitrxjava.model.AuthorResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.AuthorStats;
import it.cosenonjaviste.introtoretrofitrxjava.model.Post;
import it.cosenonjaviste.introtoretrofitrxjava.model.PostResponse;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class RetrofitTest {
    @Test public void test() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Scheduler scheduler = Schedulers.from(executor);
        WordPressService service = createService();
        service
                .listAuthors()
                .map(AuthorResponse::getAuthors)
                .flatMapIterable(l -> l)
                .limit(5)
                .flatMap(author -> service
                                .listAuthorPosts(author.getId(), 1)
                                .map(PostResponse::getPosts)
                                .flatMapIterable(l -> l)
                                .flatMapIterable(Post::getCategories)
                                .distinct()
                                .toSortedList((c1, c2) -> c1.getTitle().compareToIgnoreCase(c2.getTitle()))
                                .map(l -> new AuthorStats(author, l))
                                .subscribeOn(scheduler)
                )
                .subscribe(System.out::println, Throwable::printStackTrace);

        executor.awaitTermination(100, TimeUnit.SECONDS);
    }

    private WordPressService createService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.cosenonjaviste.it/")
                .setExecutors(Runnable::run, null)
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .build();
        restAdapter.setLogLevel(RestAdapter.LogLevel.NONE);
        return restAdapter.create(WordPressService.class);
    }
}
