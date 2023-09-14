package com.muhammadfiqrit.quranku.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.muhammadfiqrit.quranku.core.data.source.local.entity.SuratEntity
import com.muhammadfiqrit.quranku.core.data.source.local.entity.detail.AyatEntity
import com.muhammadfiqrit.quranku.core.data.source.local.entity.detail.SuratSelanjutnyaEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface SuratDao {

    //Metode untuk entitas SuratEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurat(surat: List<SuratEntity>)

    @Query("SELECT * FROM surat")
    fun getAllSurat(): Flow<List<SuratEntity>>

    @Query("SELECT * FROM surat WHERE nomor = :nomorSurat")
    fun getSuratByNomor(nomorSurat: Int): Flow<SuratEntity>

    @Query("SELECT * FROM surat where isFavorite = 1")
    fun getFavoriteSurat(): Flow<List<SuratEntity>>

    @Update
    fun updateFavoriteSurat(surat: SuratEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailSurat(surat: SuratEntity)

    @Query("SELECT * FROM ayat WHERE nomorSurat = :nomorSurat")
    fun getAyatBySurat(nomorSurat: Int): Flow<List<AyatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAyat(ayat: List<AyatEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuratSelanjutnya(suratSelanjutnya: SuratSelanjutnyaEntity)

    @Query("SELECT * FROM surat_selanjutnya WHERE nomorSurat = :nomorSurat ")
    fun getSuratSelanjutnya(nomorSurat: Int): Flow<SuratSelanjutnyaEntity>

}