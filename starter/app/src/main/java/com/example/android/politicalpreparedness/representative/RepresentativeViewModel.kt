package com.example.android.politicalpreparedness.representative

import android.app.Application
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel(application: Application) : AndroidViewModel(application) {

    val representatives: MutableLiveData<List<Representative>> = MutableLiveData()
    var address = MutableLiveData<Address>()

    fun getRepresentatives() {
        if (address.value != null) {
            viewModelScope.launch {
                try {
                    val (offices, officials) = CivicsApi.retrofitService.getRepresentativesAsync(address.value!!.toFormattedString()).await()
                    representatives.postValue(offices.flatMap { office ->
                        office.getRepresentatives(officials)
                    })
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepresentativeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RepresentativeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}

