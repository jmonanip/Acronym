package com.mydomain.acronymsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Acronym {
    @SerializedName("lf")
    @Expose
    var lf: String? = null

    @SerializedName("freq")
    @Expose
    var freq: String? = null

    @SerializedName("since")
    @Expose
    var since: String? = null

    fun getLForm() : String? {
        return lf
    }

    fun getFreqVal() : String? {
        return freq
    }

    fun getSinceVal() : String? {
        return since
    }
}