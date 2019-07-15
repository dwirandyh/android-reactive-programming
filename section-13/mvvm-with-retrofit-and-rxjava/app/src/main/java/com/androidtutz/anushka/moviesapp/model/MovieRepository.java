package com.androidtutz.anushka.moviesapp.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.androidtutz.anushka.moviesapp.R;
import com.androidtutz.anushka.moviesapp.service.MoviesDataService;
import com.androidtutz.anushka.moviesapp.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieRepository {

    private Application application;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
    private ArrayList<Movie> movies;
    private Observable<MovieDBResponse> movieDBResponseObservable;


    public MovieRepository(Application application) {

        movies = new ArrayList<>();
        MoviesDataService getMoviesDataService = RetrofitInstance.getService();
        movieDBResponseObservable = getMoviesDataService.getPopularMoviesWithRx(application.getString(R.string.api_key));


        // with flatMap to change movieDBResponse into Movies
        compositeDisposable.add(
                movieDBResponseObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<MovieDBResponse, ObservableSource<Movie>>() {
                            @Override
                            public ObservableSource<Movie> apply(MovieDBResponse movieDBResponse) throws Exception {
                                return Observable.fromArray(movieDBResponse.getMovies().toArray(new Movie[0]));
                            }
                        })
                        // filter movie rate > 7.0
                        .filter(new Predicate<Movie>() {
                            @Override
                            public boolean test(Movie movie) throws Exception {
                                return movie.getVoteAverage() > 7.0;
                            }
                        })
                        .subscribeWith(new DisposableObserver<Movie>() {
                            @Override
                            public void onNext(Movie movie) {
                                movies.add(movie);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("MainActivity", e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                moviesLiveData.postValue(movies);
                            }
                        })
        );
    }

    public MutableLiveData<List<Movie>> getMoviesLiveData() {
        return moviesLiveData;
    }

    public void clear(){
        compositeDisposable.clear();
    }
}
