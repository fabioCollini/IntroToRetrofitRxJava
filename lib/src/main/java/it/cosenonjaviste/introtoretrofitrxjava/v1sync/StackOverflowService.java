package it.cosenonjaviste.introtoretrofitrxjava.v1sync;

import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import retrofit.http.GET;
import retrofit.http.Path;

public interface StackOverflowService {

    @GET("/users") UserResponse getTopUsers();

    @GET("/users/{userId}/top-tags") TagResponse getTags(@Path("userId") int userId);

    @GET("/users/{userId}/badges") BadgeResponse getBadges(@Path("userId") int userId);
}
