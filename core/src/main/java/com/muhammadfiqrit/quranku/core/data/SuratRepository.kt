package com.muhammadfiqrit.quranku.core.data

import android.content.Context
import android.util.Log
import com.muhammadfiqrit.quranku.core.data.source.local.LocalDataSource
import com.muhammadfiqrit.quranku.core.data.source.remote.RemoteDataSource
import com.muhammadfiqrit.quranku.core.data.source.remote.network.ApiResponse
import com.muhammadfiqrit.quranku.core.data.source.remote.response.detail.DataDetailSuratResponse
import com.muhammadfiqrit.quranku.core.data.source.remote.response.surat.SuratResponse
import com.muhammadfiqrit.quranku.core.domain.model.detail.DetailSurat
import com.muhammadfiqrit.quranku.core.domain.model.surat.Surat
import com.muhammadfiqrit.quranku.core.domain.repository.ISuratRepository
import com.muhammadfiqrit.quranku.core.utils.AppExecutors
import com.muhammadfiqrit.quranku.core.utils.DataMapper
import com.muhammadfiqrit.quranku.core.utils.Utilities.isInternetConnected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class SuratRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,

    ) : ISuratRepository {
    override fun getAllSurat(): Flow<com.muhammadfiqrit.quranku.core.data.Resource<List<Surat>>> =
        object : com.muhammadfiqrit.quranku.core.data.NetworkBoundResource<List<Surat>, List<SuratResponse>>() {
            override fun loadFromDB(): Flow<List<Surat>> {
                return localDataSource.getAllSurat().map { DataMapper.mapSuratEntitiesToSurats(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<SuratResponse>>> =
                remoteDataSource.getAllSurat()


            override suspend fun saveCallResult(data: List<SuratResponse>) {
                val suratList = DataMapper.mapSuratResponsesToSuratEntities(data)
                localDataSource.insertSurat(suratList)
            }

            override fun shouldFetch(data: List<Surat>?): Boolean =
                data.isNullOrEmpty()

        }.asFlow()

    override fun getSuratByNomor(nomorSurat: Int): Flow<Resource<DetailSurat>> {


        return object : NetworkBoundResource<DetailSurat, DataDetailSuratResponse>() {
            override fun loadFromDB(): Flow<DetailSurat> {
                val suratFlow = localDataSource.getSuratByNomor(nomorSurat)
                    .map { DataMapper.mapSuratEntityToDetailSurat(it) }

                val ayatFlow = localDataSource.getAyatBySurat(nomorSurat)
                    .map { DataMapper.mapAyatEntitiesToAyat(it) }

                val suratSelanjutnyaFlow = localDataSource.getSuratSelanjutnya(nomorSurat).map {
                    DataMapper.suratSelanjutnyaEntityToSuratSelanjutnya(
                        it,
                    )
                }
                return combine(
                    suratFlow,
                    ayatFlow,
                    suratSelanjutnyaFlow
                ) { surat, ayat, suratSelanjutnya ->
                    // Now you have all three data types you need
                    DetailSurat(surat, ayat, suratSelanjutnya)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<DataDetailSuratResponse>> {
                return remoteDataSource.getDetailSurat(nomorSurat)
            }

            override suspend fun saveCallResult(data: DataDetailSuratResponse) {
                val surat = DataMapper.mapDataDetailSuratResponseToSuratEntity(data)
                val ayat = DataMapper.mapAyatResponsesToAyatEntities(data.ayat, nomorSurat)
                val suratSelanjutnya =
                    DataMapper.suratSelanjutnyaResponseToSuratSelanjutnyaEntities(
                        data.suratSelanjutnyaResponse,
                        nomorSurat
                    )

                localDataSource.insertDetailSurat(surat)
                localDataSource.insertAyat(ayat)
                localDataSource.insertSuratSelanjutnya(suratSelanjutnya = suratSelanjutnya)
            }

            override fun shouldFetch(data: DetailSurat?): Boolean {

                //sama kaya data?.ayat != null
                //pengambilan data di internet terus

                return data != null

            }

        }.asFlow()
    }


    override fun getFavoriteSurat(): Flow<List<Surat>> {
        return localDataSource.getFavoriteSurat().map {
            DataMapper.mapSuratEntitiesToSurats(it)
        }
    }

    override fun setFavoriteSurat(surat: DetailSurat, newState: Boolean) {
        val suratEntity = DataMapper.mapDetailSuratToSuratEntity(surat)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteSurat(suratEntity, newState)
        }
    }
}