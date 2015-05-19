package it.cosenonjaviste.introtoretrofitrxjava.v2callbacks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowServiceFactory;
import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserStats;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        StackOverflowService service = StackOverflowServiceFactory.createService(StackOverflowService.class, executor);

        service.getTopUsers(new Callback<UserResponse>() {
            @Override public void success(UserResponse userResponse, Response response) {
                loadUserStats(service, userResponse.getItems().get(0), new Callback<UserStats>() {
                    @Override public void success(UserStats userStats, Response response) {
                        System.out.println(userStats);
                        executor.shutdown();
                    }

                    @Override public void failure(RetrofitError error) {
                        error.printStackTrace();
                        executor.shutdown();
                    }
                });
            }

            @Override public void failure(RetrofitError error) {
                error.printStackTrace();
                executor.shutdown();
            }
        });

        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    private static UserStats loadUserStats(StackOverflowService service, User user, Callback<UserStats> callback) {
        service.getTags(user.getId(), new Callback<TagResponse>() {
            @Override public void success(final TagResponse tags, Response response) {
                service.getBadges(user.getId(), new Callback<BadgeResponse>() {
                    @Override public void success(BadgeResponse badges, Response response) {
                        callback.success(new UserStats(user, tags.getItems(), badges.getItems()), response);
                    }

                    @Override public void failure(RetrofitError error) {
                        callback.failure(error);
                    }
                });
            }

            @Override public void failure(RetrofitError error) {
                callback.failure(error);
            }
        });
        return null;
    }
}
