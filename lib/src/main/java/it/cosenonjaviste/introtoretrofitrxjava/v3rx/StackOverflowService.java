package it.cosenonjaviste.introtoretrofitrxjava.v3rx;

import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface StackOverflowService {

    @GET("/users") Observable<UserResponse> getTopUsers();

    @GET("/users/{userId}/top-tags") Observable<TagResponse> getTags(@Path("userId") int userId);

    @GET("/users/{userId}/badges") Observable<BadgeResponse> getBadges(@Path("userId") int userId);
}
