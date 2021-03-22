package com.mydomain.acronymsapp.api

import com.mydomain.acronymsapp.model.AcronymsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApiInterface {
    @GET("dictionary.py")
    fun lookupAcronym(
        @Query("sf") sf: String
    ): Call<List<AcronymsList>>
}