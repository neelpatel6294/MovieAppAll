package com.example.movieappre;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.example.movieappre.RoomDatabase.AppDatabase;
import com.example.movieappre.RoomDatabase.MovieDao;
import com.example.movieappre.database.RemoteNetworkCall;
import com.example.movieappre.model.Movies.MoviesResult;
import com.example.movieappre.model.Reviews.ReviewResult;
import com.example.movieappre.model.Trailer.TrailerResult;

import java.util.List;

public class Respository {

    private MovieDao mMovieDao;

    private LiveData<List<MoviesResult>> mData;
    private LiveData<List<MoviesResult>> mDataFav;
    private int movieID;
    private LiveData<List<ReviewResult>> mReviewResult;
    private LiveData<List<TrailerResult>> mTrailerResult;


    // constructor for movie
    public Respository(Application application) {

        AppDatabase db = AppDatabase.getDatabase(application);
        mMovieDao = db.movieDao();

        RemoteNetworkCall.fetchData("popular");


    }

    // constructor for review and trailer

    public Respository(int movie1ID, Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        mMovieDao = db.movieDao();
        this.movieID = movie1ID;
        RemoteNetworkCall.fetchMovieReview(movieID);
        RemoteNetworkCall.fetchMovieTrailer(movieID);

    }


    // Methods for MainActivity

    public LiveData<List<MoviesResult>> mLiveData() {
        mData = RemoteNetworkCall.getIntData();

        return mData;
    }


    public void getFavData() {
        mDataFav = mMovieDao.getAllFav();

    }

    public LiveData<List<MoviesResult>> mLiveDataFav() {

        return mDataFav;
    }

    public void getTopRated() {
        RemoteNetworkCall.fetchData("top_rated");
    }

    public void getPopular() {
        RemoteNetworkCall.fetchData("popular");
    }


    public void deleteData(int id) {

        new deleteAsyncTask(mMovieDao).execute(id);

    }


    //----------------------------------------------------------------------------------


    public void insert(MoviesResult result) {

        new insertAsyncTask(mMovieDao).execute(result);
    }


    class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private MovieDao mMovieDao;


        public deleteAsyncTask(MovieDao movieDao) {
            mMovieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mMovieDao.delete(integers[0]);

            return null;
        }
    }


    class insertAsyncTask extends AsyncTask<MoviesResult, Void, Void> {

        private MovieDao mMovieDao;

        public insertAsyncTask(MovieDao movieDao) {

            mMovieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MoviesResult... results) {

            mMovieDao.insert(results[0]);

            return null;
        }
    }

    //Methods for Detail Activity


    public LiveData<List<ReviewResult>> mReviewLiveData() {
        mReviewResult = RemoteNetworkCall.getReviewsData();

        return mReviewResult;
    }

    public LiveData<List<TrailerResult>> mTrailerLiveData() {
        mTrailerResult = RemoteNetworkCall.getTrailerData();

        return mTrailerResult;
    }




}
