package com.example.tugasakhirpam.data

import kotlinx.serialization.Serializable

@Serializable
data class Penulis(
    val id_penulis: Int,
    val nama_penulis: String,
    val biografi: String,
    val kontak: String
)


