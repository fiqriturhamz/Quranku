package com.muhammadfiqrit.quranku.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muhammadfiqrit.quranku.data.SuratRepository
import com.muhammadfiqrit.quranku.di.Injection
import com.muhammadfiqrit.quranku.domain.usecase.SuratUseCase

class ViewModelFactory private constructor(private val suratUseCase: SuratUseCase) : ViewModelProvider.NewInstanceFactory() {
    companion object{
        @Volatile
        private var instance : ViewModelFactory? = null
        fun getInstance(context:Context) : ViewModelFactory = instance ?: synchronized(this){
            instance ?: ViewModelFactory(Injection.provideSuratUseCase(context))
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when{
            modelClass.isAssignableFrom(SuratViewModel::class.java) -> {
                SuratViewModel(suratUseCase) as T
            }else -> throw  Throwable("Unknown ViewModel class : " + modelClass.name)
        }
}