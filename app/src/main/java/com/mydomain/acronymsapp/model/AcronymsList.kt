package com.mydomain.acronymsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AcronymsList {
    @SerializedName("sf")
    @Expose
    val sf: String? = null

    @SerializedName("lfs")
    @Expose
    val lfsList: List<Acronym>? = null

    fun getLfList(): List<Acronym>? {
        return lfsList
    }

    fun getSForm() : String? {
        return sf
    }
}