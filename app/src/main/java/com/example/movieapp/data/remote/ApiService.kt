package com.example.movieapp.data.remote

import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieResponse
import com.example.movieapp.data.model.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.movieapp.data.model.CastResponse


interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>


    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<VideoResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<Movie>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<CastResponse>

    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genre: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<Movie>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<CastResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Response<MovieResponse>
}


