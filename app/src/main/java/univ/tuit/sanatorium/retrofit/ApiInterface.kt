package univ.tuit.sanatorium.retrofit

import retrofit2.Call
import retrofit2.http.GET
import univ.tuit.sanatorium.models.SanatoriumFullData
import univ.tuit.sanatorium.models.SanatoriumTinyData

interface ApiInterface {

    @GET("sanatory")
    fun allSanatories(): Call<List<SanatoriumFullData>>

    @GET("sanatory/tiny")
    fun allSanatoriesTiny(): Call<List<SanatoriumTinyData>>
}