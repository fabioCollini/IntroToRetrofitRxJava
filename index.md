---

layout: ribbon

style: |

    #Cover h2 {
        margin:30px 0 0;
        color:#FFF;
        text-align:center;
        font-size:70px;
        }
    #Cover p {
        margin:10px 0 0;
        text-align:center;
        color:#FFF;
        font-style:italic;
        font-size:20px;
        }
        #Cover p a {
            color:#FFF;
            }
    #Picture h2 {
        color:#FFF;
        }
    #SeeMore h2 {
        font-size:100px
        }
    #SeeMore img {
        width:0.72em;
        height:0.72em;
        }
    .slide>div {
        padding-top:25px;
        height: 545px;
    }
    .slide pre code {
        line-height: 35px;
    }
    .slide.smallSize pre code {
        line-height: 25px;
        font-size: 18px;
    }
    .slide:after {
        background: none;
    }
    img[alt=marble] { width: 750px; }
    .slide pre mark.comment span {
      padding: 0;
      background: 0 0;
      color: #999;
    }
---

# Introduction to Retrofit and RxJava {#Cover}

*Fabio Collini*

*Word in progress...*

![](pictures/cover.jpg)
<!-- photo by John Carey, fiftyfootshadows.net -->

## Retrofit

## RxJava is not so simple...

![](pictures/rx_twitter.png)

## Server call definition

    public interface StackOverflowService {

      @GET("/users")
      UserResponse getTopUsers();

    }

## Service creation

    RestAdapter restAdapter = new RestAdapter.Builder()
      .setEndpoint("http://api.stackexchange.com/2.2/")
      .setRequestInterceptor(request -> {
        request.addQueryParam("site", "stackoverflow");
        request.addQueryParam("key", "...");
      })
      .build();
    restAdapter.setLogLevel(RestAdapter.LogLevel.BASIC);
    StackOverflowService service = 
      restAdapter.create(StackOverflowService.class);


## Chiamata sincrona

    private List<User> loadItemsSync() {
      List<User> users = service.getTopUsersSync()
        .getItems();
      if (users.size() > 5) {
        users = users.subList(0, 5);
      }
      return users;
    }

## AsyncTask
{:.smallSize}

    new AsyncTask<Void, Void, List<User>>() {
      @Override 
      protected List<User> doInBackground(Void... p) {
        try {
          return loadItemsSync();
        } catch (Exception e) {
          return null;
        }
      }
      @Override
      protected void onPostExecute(List<User> users) {
        if (users != null) {
          adapter.addAll(users);
        } else {
          showError();
        }
      }
    }.execute();

## Server call parameters

    @GET("/users/{userId}/top-tags") 
    TagResponse getTagsSync(
      @Path("userId") int userId
    );
  
    @GET("/users/{userId}/badges") 
    BadgeResponse getBadgesSync(
      @Path("userId") int userId
    );

## Server call compositions

    List<User> users = service.getTopUsers().getItems();
    if (users.size() > 5) {
      users = users.subList(0, 5);
    }
    List<UserStats> statsList = new ArrayList<>();
    for (User user : users) {
      TagResponse tags = 
        service.getTagsSync(user.getId());
      BadgeResponse badges = 
        service.getBadgesSync(user.getId());
      statsList.add(new UserStats(user, 
        tags.getItems(), badges.getItems()));
    }
    return statsList;

## Callbacks

    public interface StackOverflowService {

      @GET("/users") 
      void getTopUsers(Callback<UserResponse> callback);

    }

## 03

    service.getTopUsers(new Callback<UserResponse>() {
      @Override public void success(
          UserResponse repoResponse, Response r) {
        List<User> users = repoResponse.getItems();
        if (users.size() > 5)
          users = users.subList(0, 5);
        adapter.addAll(users);
      }
      @Override 
      public void failure(RetrofitError error) {
        showError();
      }
    });

## 03

    service.getTopUsers(new Callback<UserResponse>() {
      @Override public void success(
          UserResponse repoResponse, Response r) {
        <mark>List<User> users = repoResponse.getItems();</mark>
        <mark>if (users.size() > 5)</mark>
        <mark>  users = users.subList(0, 5);</mark>
        <mark>adapter.addAll(users);</mark>
      }
      @Override 
      public void failure(RetrofitError error) {
        showError();
      }
    });

## 03

    service.getTopUsers(new Callback<UserResponse>() {
      @Override public void success(
          UserResponse repoResponse, Response r) {
        List<User> users = repoResponse.getItems();
        if (users.size() > 5)
          users = users.subList(0, 5);
        adapter.addAll(users);
      }
      @Override 
      public void failure(RetrofitError error) {
        <mark>showError();</mark>
      }
    });

## Callback hell
{:.smallSize}

    service.getBadges(userId, new Callback<BadgeResponse>() {
      @Override public void success(BadgeResponse badges, Response r) {
        service.getTags(userId, new Callback<TagResponse>() {
          @Override public void success(TagResponse tags, Response r) {
            callback.success(new UserStats(user, 
              tags.getItems(), badges.getItems()), r);
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

## Service Rx

    public interface StackOverflowService {

      @GET("/users")
      Observable<UserResponse> getTopUsers();

    }

## 04

    service
      .getTopUsers()
      .subscribe(new Action1<UserResponse>() {
        @Override public void call(UserResponse r) {
          adapter.addAll(r.getItems());
        }
      }, new Action1<Throwable>() {
        @Override public void call(Throwable t) {
          showError();
        }
      });

## Java 8

    service
        .getTopUsers()
        .subscribe(
          r -> adapter.addAll(r.getItems()), 
          t -> showError()
        );

## Observable con 3 callback

## Threading

    service
        .getTopUsers()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
          r -> adapter.addAll(r.getItems()), 
          t -> showError()
        );

## Map

![marble](pictures/map.png)

## Map

    service.getTopUsers()
      .subscribe(r -> adapter.addAll(r.getItems()));

## Map

    service.getTopUsers()
      .subscribe(r -> adapter.addAll(r.getItems()));

    service.getTopUsers()
        <mark>.map(r -> r.getItems())</mark>
        .subscribe(items -> adapter.addAll(items));

## Map

    service.getTopUsers()
      .subscribe(r -> adapter.addAll(r.getItems()));

    service.getTopUsers()
        .map(r -> r.getItems())
        .subscribe(items -> adapter.addAll(items));

    service.getTopUsers()
        .map(UserResponse::getItems)
        .subscribe(adapter::addAll);

## FlatMap

![marble](pictures/flatMap.png)

## FlatMap

    <mark class="comment">private Observable<UserStats> loadRepoStats(</mark>
    <mark class="comment">    User user) {
    <mark class="comment">  return</mark> service.getTags(user.getId())
          .map(TagResponse::getItems)
    <mark class="comment">      .flatMap(tags -></mark>
    <mark class="comment">          service.getBadges(user.getId())</mark>
    <mark class="comment">              .map(BadgeResponse::getItems)</mark>
    <mark class="comment">              .map(badges -> </mark>
    <mark class="comment">                new UserStats(user, tags, badges)</mark>
    <mark class="comment">              )</mark>
    <mark class="comment">      );</mark>
    <mark class="comment">}</mark>

## FlatMap

    <mark class="comment">private Observable<UserStats> loadRepoStats(</mark>
    <mark class="comment">    User user) {
    <mark class="comment">  return</mark> service.getTags(user.getId())
          .map(TagResponse::getItems)
          .flatMap(tags ->
    <mark class="comment">          service.getBadges(user.getId())</mark>
    <mark class="comment">              .map(BadgeResponse::getItems)</mark>
    <mark class="comment">              .map(badges -> </mark>
    <mark class="comment">                new UserStats(user, tags, badges)</mark>
    <mark class="comment">              )</mark>
          );
    <mark class="comment">}</mark>

## FlatMap

    <mark class="comment">private Observable<UserStats> loadRepoStats(</mark>
    <mark class="comment">    User user) {
    <mark class="comment">  return</mark> service.getTags(user.getId())
          .map(TagResponse::getItems)
          .flatMap(tags ->
              service.getBadges(user.getId())
                  .map(BadgeResponse::getItems)
    <mark class="comment">              .map(badges -> </mark>
    <mark class="comment">                new UserStats(user, tags, badges)</mark>
    <mark class="comment">              )</mark>
          );
    <mark class="comment">}</mark>

## FlatMap

    private Observable<UserStats> loadRepoStats(
        User user) {
      return service.getTags(user.getId())
          .map(TagResponse::getItems)
          .flatMap(tags ->
              service.getBadges(user.getId())
                  .map(BadgeResponse::getItems)
                  .map(badges -> 
                    new UserStats(user, tags, badges)
                  )
          );
    }

## FlatMap

    service
        .getTopUsers()

## FlatMap

    service
        .getTopUsers()
        .flatMap(r -> Observable.from(r.getItems()))

## FlatMap

    service
        .getTopUsers()
        .flatMapIterable(UserResponse::getItems)

## FlatMap

    service
        .getTopUsers()
        .flatMapIterable(UserResponse::getItems)
        .limit(5)

## FlatMap

    service
        .getTopUsers()
        .flatMapIterable(UserResponse::getItems)
        .limit(5)
        .flatMap(this::loadRepoStats)

## FlatMap

    service
        .getTopUsers()
        .flatMapIterable(UserResponse::getItems)
        .limit(5)
        .flatMap(this::loadRepoStats)
        .toList();

## FlatMap implementation

    public final <R> Observable<R> flatMap(
          Func1<
            ? super T, 
            ? extends Observable<? extends R>
          > func) {
      <mark>return merge(map(func));</mark>
    }

## ConcatMap

![marble](pictures/concatMap.png)

## ConcatMap implementation

    public final <R> Observable<R> concatMap(
          Func1<
            ? super T, 
            ? extends Observable<? extends R>
          > func) {
        <mark>return concat(map(func));</mark>
    }

## FlatMap

    service
        .getTopUsers()
        .flatMapIterable(UserResponse::getItems)
        .limit(5)
        .<mark>concatMap</mark>(this::loadRepoStats)
        .toList();

## zip

![marble](pictures/zip.png)

## flatMap -> zip

    service.getTags(user.getId())
        .map(TagResponse::getItems)
        .flatMap(tags ->
            service.getBadges(user.getId())
                .map(BadgeResponse::getItems)
                .map(badges -> 
                  new UserStats(user, tags, badges)
                )
        );

## flatMap -> zip

    Observable.zip(
        <mark class="comment">service.getTags(user.getId())</mark>
        <mark class="comment">  .map(TagResponse::getItems),</mark>
        <mark class="comment">service.getBadges(user.getId())</mark>
        <mark class="comment">  .map(BadgeResponse::getItems),</mark>
        <mark class="comment">(tags, badges) -> </mark>
        <mark class="comment">  new UserStats(user, tags, badges)</mark>
    );        

## flatMap -> zip

    Observable.zip(
        <mark>service.getTags(user.getId())</mark>
        <mark>  .map(TagResponse::getItems),</mark>
        <mark class="comment">service.getBadges(user.getId())</mark>
        <mark class="comment">  .map(BadgeResponse::getItems),</mark>
        <mark class="comment">(tags, badges) -> </mark>
        <mark class="comment">  new UserStats(user, tags, badges)</mark>
    );        

## flatMap -> zip

    Observable.zip(
        service.getTags(user.getId())
          .map(TagResponse::getItems),
        <mark>service.getBadges(user.getId())</mark>
        <mark>  .map(BadgeResponse::getItems),</mark>
        <mark class="comment">(tags, badges) -> </mark>
        <mark class="comment">  new UserStats(user, tags, badges)</mark>
    );        

## flatMap -> zip

    Observable.zip(
        service.getTags(user.getId())
          .map(TagResponse::getItems),
        service.getBadges(user.getId())
          .map(BadgeResponse::getItems),
        <mark>(tags, badges) -> </mark>
        <mark>  new UserStats(user, tags, badges)</mark>
    );        

## flatMap -> zip

    Observable.zip(
        service.getTags(user.getId())
          .map(TagResponse::getItems),
        service.getBadges(user.getId())
          .map(BadgeResponse::getItems),
        (tags, badges) -> 
          new UserStats(user, tags, badges)
    );        

## subscription

## cold/hot

## ciclo di vita activity?

## Shower Key Features

1. Built on HTML, CSS and vanilla JavaScript
2. All modern browsers are supported
3. Slide themes are separated from engine
4. Fully keyboard accessible
5. Printable to PDF

{:.note}
Shower ['ʃəuə] noun. A person or thing that shows.


## Plain Text on Your Slides

Lorem ipsum dolor sit amet, consectetur [adipisicing](#all-kind-of-lists) elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, *quis nostrud* exercitation ullamco laboris **nisi ut aliquip** ex ea commodo consequat. Duis aute irure <i>dolor</i> in reprehenderit in voluptate velit esse cillum <b>dolore</b> eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in `<culpa>` qui officia deserunt mollit anim id est laborum.

## All Kind of Lists

1. Simple lists are marked with bullets
2. Ordered lists begin with a number
3. You can even nest lists one inside another
    - Or mix their types
    - But do not go too far
    - Otherwise audience will be bored
4. Look, seven rows exactly!

## Serious Citations

<figure markdown="1">

> Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia.

<figcaption>Marcus Tullius Cicero</figcaption>
</figure>

## Code Samples

    <!DOCTYPE html>
    <html lang="en">
    <mark><head></mark> <mark class="comment"><!--Comment--></mark>
        <title>Shower</title>
        <meta charset="<mark class="important">UTF-8</mark>">
        <link rel="stylesheet" href="screen.css">
    <mark></head></mark>

## Even Tables

|  Locavore      | Umami       | Helvetica | Vegan     |
+----------------|-------------|-----------|-----------+
|* Fingerstache *| Kale        | Chips     | Keytar    |
|* Sriracha     *| Gluten-free | Ennui     | Keffiyeh  |
|* Thundercats  *| Jean        | Shorts    | Biodiesel |
|* Terry        *| Richardson  | Swag      | Blog      |

It’s good to have information organized.

## Pictures
{:.cover #Picture}

![](pictures/picture.jpg)
<!-- photo by John Carey, fiftyfootshadows.net -->

## **You can even shout this way**

## Inner Navigation

1. Lets you reveal list items one by one
2. …To keep some key points
3. …In secret from audience
4. …But it will work only once
5. …Nobody wants to see the same joke twice

## ![](http://shwr.me/pictures/logo.svg) [See more on GitHub](https://github.com/shower/shower/)
{:.shout #SeeMore}
