package com.example.movie.data

data class Base(
    val Query: String?,
    val KMAQuery: String?,
    val TotalCount: Number?,
    val Data: List<Data>?
)

data class Data(
    val CollName: String?,
    val TotalCount: Number?,
    val Count: Number?,
    val Result: List<Result>?
)

data class Result(
    val DOCID: String?,
    val movieId: String?,
    val movieSeq: String?,
    val title: String?,
    val titleEng: String?,
    val titleOrg: String?,
    val titleEtc: String?,
    val prodYear: String?,
    val directors: Directors?,
    val actors: Actors?,
    val nation: String?,
    val company: String?,
    val plots: Plots?,
    val runtime: String?,
    val rating: String?,
    val genre: String?,
    val kmdbUrl: String?,
    val repRlsDate : String,
    val repRatDate : String,
    val posters : String,
    val releaseDate : String
)

data class Actors(
    val actor: List<Actor>?
)

data class Actor(
    val actorNm: String?,
    val actorEnNm: String?,
    val actorId: String?
)

data class Directors(
    val director: List<Director>?
)

data class Director(
    val directorNm: String?,
    val directorEnNm: String?,
    val directorId: String?
)

data class Plots(
    val plot: List<Plot>?
)

data class Plot(
    val plotLang: String?,
    val plotText: String?
)