package com.example.gamesearch.webservice

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GameSearchApi {
    @GET("games/")
    suspend fun fetchAutocompleteGameList(@QueryMap params: Map<String, String>): Response<GamesApiResponse?>
}