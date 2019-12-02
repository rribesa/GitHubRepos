package br.com.rrs.githubrepos.lista

import br.com.rrs.githubrepos.lista.model.data.response.GitListaRepositorioResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val QUERY = "q"
const val SORT = "sort"
const val KOTLIN_LANGUAGE = "language%3Akotlin"
const val STARS = "stars"
const val PAGE = "page"
const val INITIAL_PAGE = 1

interface Service {
    @GET("search/repositories")
    suspend fun getHubRepos(
        @Query(QUERY, encoded = true) query: String = KOTLIN_LANGUAGE,
        @Query(SORT) sort: String = STARS,
        @Query(PAGE) page: Int = INITIAL_PAGE
    ): GitListaRepositorioResponse
}
