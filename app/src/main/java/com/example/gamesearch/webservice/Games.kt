package com.example.gamesearch.webservice

import androidx.annotation.Keep

// data classes that get data that is needed from json
@Keep
data class GamesApiResponse(
    val results: List<Games>
)

@Keep
data class Games(
    val name: String,
    val image: ImageUrls,
    val deck: String
)

@Keep
data class ImageUrls(
    val icon_url: String,
    val super_url: String
)