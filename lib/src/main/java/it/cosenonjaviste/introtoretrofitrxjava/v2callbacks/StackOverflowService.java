package it.cosenonjaviste.introtoretrofitrxjava.v2callbacks;

import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface StackOverflowService {

    @GET("/users") void getTopUsers(Callback<UserResponse> callback);

    @GET("/users/{userId}/top-tags") void getTags(@Path("userId") int userId, Callback<TagResponse> callback);

    @GET("/users/{userId}/badges") void getBadges(@Path("userId") int userId, Callback<BadgeResponse> callback);
}
