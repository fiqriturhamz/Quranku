package com.muhammadfiqrit.quranku.core.data.source.remote.response.sholat.jadwal

import com.google.gson.annotations.SerializedName

data class ResponseJadwal(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("imsak")
    val imsak: String,

    @field:SerializedName("isya")
    val isya: String,

    @field:SerializedName("dzuhur")
    val dzuhur: String,

    @field:SerializedName("subuh")
    val subuh: String,

    @field:SerializedName("dhuha")
    val dhuha: String,

    @field:SerializedName("terbit")
    val terbit: String,

    @field:SerializedName("tanggal")
    val tanggal: String,

    @field:SerializedName("ashar")
    val ashar: String,

    @field:SerializedName("maghrib")
    val maghrib: String
)
