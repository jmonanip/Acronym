package com.mydomain.acronymsapp.lookup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.discoverrestaurantapp.api.AcronymsServiceClient
import com.mydomain.acronymsapp.api.ServerApiInterface
import com.mydomain.acronymsapp.model.AcronymsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LookupAcronymViewModel : ViewModel() {
    private val acronymListData: MutableLiveData<AcronymsList> = MutableLiveData()
    private val errorStatus = SingleLiveEvent<String>()
    fun getAcronymList(): MutableLiveData<AcronymsList> {
        return acronymListData
    }

    fun getErrorStatus() : SingleLiveEvent<String> {
        return errorStatus;
    }

    fun lookupAcronym(acronym: String) {
        if (AcronymsServiceClient.client == null)
            return

        val lookupApi: ServerApiInterface =
            AcronymsServiceClient.client!!.create(ServerApiInterface::class.java)
        val call: Call<List<AcronymsList>> = lookupApi.lookupAcronym(acronym)
        call.enqueue(object : Callback<List<AcronymsList>> {
            override fun onFailure(call: Call<List<AcronymsList>>?, t: Throwable?) {
                Log.e(TAG, "onFailure: ", t)
                errorStatus.postValue("Server Exception: " + t?.message)
            }

            override fun onResponse(
                call: Call<List<AcronymsList>>,
                response: Response<List<AcronymsList>>
            ) {
                if (response.isSuccessful) {
                    val acronymsList: List<AcronymsList>? = response.body()
                    if (!acronymsList.isNullOrEmpty()) {
                        acronymListData.postValue(acronymsList[0])
                    } else {
                        Log.e(
                            TAG,
                            "onResponse: empty()"
                        )
                        clearAcronymData()
                    }
                } else {
                    Log.e(TAG, "onResponse: " + response.code())
                }
            }
        })
    }

    fun clearAcronymData() {
        acronymListData.postValue(AcronymsList())
        errorStatus.value = String()
    }

    companion object {
        const val TAG = "LookupAcronymViewModel"
    }
}