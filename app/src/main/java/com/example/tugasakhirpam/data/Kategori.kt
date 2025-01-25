package com.example.tugasakhirpam.data

import kotlinx.serialization.Serializable

@Serializable
data class Kategori(
    val id_kategori: Int,
    val nama_kategori: String,
    val deskripsi_kategori: String
)


