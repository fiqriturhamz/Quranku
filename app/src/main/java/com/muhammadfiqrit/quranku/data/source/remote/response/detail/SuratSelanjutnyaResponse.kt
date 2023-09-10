package com.muhammadfiqrit.quranku.data.source.remote.response.detail

import com.google.gson.annotations.SerializedName

data class SuratSelanjutnyaResponse(

    @field:SerializedName("jumlahAyat")
    val jumlahAyat: Int? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("nomor")
    val nomor: Int? = null,

    @field:SerializedName("namaLatin")
    val namaLatin: String? = null
)