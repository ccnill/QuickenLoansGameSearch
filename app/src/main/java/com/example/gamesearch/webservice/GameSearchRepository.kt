package com.example.gamesearch.webservice

import javax.inject.Inject
import javax.inject.Named

class GameSearchRepository @Inject constructor(
    @Named("gameService")
    private val gameSearchApi: GameSearchApi
) {
    suspend fun getGames(query: String): GamesApiResponse? {
        val response = gameSearchApi.fetchAutocompleteGameList(createQueryParams(query))

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    private fun createQueryParams(query: String): HashMap<String, String> {
        val params = HashMap<String, String>()
        params["filter"] = "name:$query"
        params["api_key"] = "9d45436f87d3848d2bdcce810bacb6df57dfd134"
        params["format"] = "json"
        return params
    }

}
