package univ.tuit.sanatorium.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = ""
    var retrofit: Retrofit? = null

    fun refreshToken() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}