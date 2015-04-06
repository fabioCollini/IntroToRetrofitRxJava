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
---

# Introduction to Retrofit and RxJava {#Cover}

*Fabio Collini*

*Word in progress...*

![](pictures/cover.jpg)
<!-- photo by John Carey, fiftyfootshadows.net -->

## Service con un solo metodo

    public interface GitHubServiceSync {

      @GET("/search/repositories?q=language:Java" +
          "&sort=updated&order=desc")
      RepoResponse listLastRepos();

    }

## Chiamata sincrona

    private List<Repo> loadItemsSync() {
      RestAdapter restAdapter = 
        new RestAdapter.Builder()
        .setEndpoint("https://api.github.com/")
        .build();
      restAdapter.setLogLevel(LogLevel.BASIC);
      GitHubServiceSync service = restAdapter.create(
        GitHubServiceSync.class);
      return service.listLastRepos().getItems();
    }

## Threading    

    public void loadItems() {
      new Thread(() -> {
        try {
          List<Repo> items = loadItemsSync();
          runOnUiThread(() -> adapter.addAll(items));
        } catch (Exception e) {
          runOnUiThread(() -> Toast.makeText(this, R.string.error_loading, Toast.LENGTH_LONG).show());
        }
      }).start();
    }

## Service multi metodo con parametri

    @GET("/repos/{owner}/{repo}/contributors")
    List<User> listContributors(
        @Path("owner") String ownerLoginName,
        @Path("repo") String repoName
    );

    @GET("/repos/{owner}/{repo}/languages")
    Map<String, String> listLanguages(
        @Path("owner") String ownerLoginName,
        @Path("repo") String repoName
    );

## 02

    RepoResponse repoResponse = service.listLastRepos();
    List<Repo> items = repoResponse.getItems();
    List<RepoStats> statsList = new ArrayList<>();
    for (Repo repo : items) {
      String login = repo.getOwner().getLogin();
      String name = repo.getName();
      List<User> users = 
          service.listContributors(login, name);
      Set<String> langs = 
          service.listLanguages(login, name).keySet();
      statsList.add(new RepoStats(name, users, langs));
    }

## Service con callback

    public interface GitHubServiceCallback {

      @GET("/search/repositories?q=language:Java" +
          "&sort=updated&order=desc") 
      void listLastRepos(Callback<RepoResponse> c);

    }

## 03

    service.listLastRepos(new Callback<RepoResponse>() {
      @Override public void success(
          RepoResponse repoResponse, Response r) {
        List<Repo> items = repoResponse.getItems();
        adapter.addAll(items);
      }

      @Override public void failure(RetrofitError e) {
        showError();
      }
    });

## Callback hell
{:.smallSize}

    service.listContributors(login, repoName, new Callback<List<User>>() {
      public void success(List<User> users, Response r) {
        service.listLanguages(login, repoName, 
            new Callback<Map<String, String>>() {
          public void success(Map<String, String> langs, Response r) {
            callback.success(
                new RepoStats(repoName, users, langs.keySet()), r);
          }
          public void failure(RetrofitError error) {
            callback.failure(error);
          }
        });
      }
      public void failure(RetrofitError error) {
        callback.failure(error);
      }
    });

## Service Rx

    public interface GitHubService {

      @GET("/search/repositories?q=language:Java" +
          "&sort=updated&order=desc")
      Observable<RepoResponse> listLastRepos();

    }

## 04

    service.listLastRepos()
        .subscribe(
          new Action1<RepoResponse>() {
            @Override public void call(RepoResponse r) {
              adapter.addAll(r.getItems());
            }
          },
          new Action1<Throwable>() {
            @Override public void call(Throwable t) {
              showError();
            }
          }
        );

## Java 8

    service.listLastRepos()
        .subscribe(
          r -> adapter.addAll(r.getItems()), 
          t -> showError()
        );

## Observable con 3 callback

## Threading

    service.listLastRepos()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
          r -> adapter.addAll(r.getItems()), 
          t -> showError()
        );


## Map

![marble](pictures/map.png)

## FlatMap

![marble](pictures/flatMap.png)

## Ordine elementi concatMap

![marble](pictures/concatMap.png)

## zip

![marble](pictures/zip.png)

## Esempio con Executor con 5 thread

## polling o ciclo di vita activity?

## interval

## subscription

## cold/hot


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
