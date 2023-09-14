package com.muhammadfiqrit.quranku.core.domain.repository

import com.muhammadfiqrit.quranku.core.data.Resource
import com.muhammadfiqrit.quranku.core.domain.model.detail.Ayat
import com.muhammadfiqrit.quranku.core.domain.model.detail.DetailSurat
import com.muhammadfiqrit.quranku.core.domain.model.surat.Surat
import kotlinx.coroutines.flow.Flow

interface ISuratRepository {
    fun getAllSurat() : Flow<com.muhammadfiqrit.quranku.core.data.Resource<List<Surat>>>

    fun getSuratByNomor(nomorSurat : Int) : Flow<com.muhammadfiqrit.quranku.core.data.Resource<DetailSurat>>



    fun getFavoriteSurat() : Flow<List<Surat>>

    fun setFavoriteSurat(surat : DetailSurat, newState:Boolean)



}