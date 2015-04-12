package it.cosenonjaviste.introtoretrofitrxjava;

import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface StackOverflowService {

    @GET("/users")
    UserResponse getTopUsersSync();

    @GET("/users/{userId}/top-tags")
    TagResponse getTagsSync(@Path("userId") int userId);

    @GET("/users/{userId}/badges")
    BadgeResponse getBadgesSync(@Path("userId") int userId);


    @GET("/users")
    void getTopUsers(Callback<UserResponse> callback);

    @GET("/users/{userId}/top-tags")
    void getTags(@Path("userId") int userId, Callback<TagResponse> callback);

    @GET("/users/{userId}/badges")
    void getBadges(@Path("userId") int userId, Callback<BadgeResponse> callback);


    @GET("/users") Observable<UserResponse> getTopUsers();

    @GET("/users/{userId}/top-tags")
    Observable<TagResponse> getTags(@Path("userId") int userId);

    @GET("/users/{userId}/badges")
    Observable<BadgeResponse> getBadges(@Path("userId") int userId);
}
