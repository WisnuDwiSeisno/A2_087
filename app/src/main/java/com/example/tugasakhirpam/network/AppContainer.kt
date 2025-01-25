package com.example.tugasakhirpam.network

import com.example.tugasakhirpam.repository.BukuRepository
import com.example.tugasakhirpam.repository.KategoriRepository
import com.example.tugasakhirpam.repository.NetworkBukuRepository
import com.example.tugasakhirpam.repository.NetworkKategoriRepository
import com.example.tugasakhirpam.repository.NetworkPenerbitRepository
import com.example.tugasakhirpam.repository.NetworkPenulisRepository
import com.example.tugasakhirpam.repository.PenerbitRepository
import com.example.tugasakhirpam.repository.PenulisRepository
import com.example.tugasakhirpam.services.BukuService
import com.example.tugasakhirpam.services.KategoriService
import com.example.tugasakhirpam.services.PenerbitService
import com.example.tugasakhirpam.services.PenulisService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class AppContainerImpl : AppContainer {

    private val baseUrl = "http://10.0.2.2:3000/api/" // localhost untuk emulator
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    // Services
    private val penulisService: PenulisService by lazy { retrofit.create(PenulisService::class.java) }
    private val penerbitService: PenerbitService by lazy { retrofit.create(PenerbitService::class.java) }
    private val bukuService: BukuService by lazy { retrofit.create(BukuService::class.java) }
    private val kategoriService: KategoriService by lazy { retrofit.create(KategoriService::class.java) }

    // Repositories
    override val penulisRepository: PenulisRepository by lazy {
        NetworkPenulisRepository(penulisService)
    }

    override val penerbitRepository: PenerbitRepository by lazy {
        NetworkPenerbitRepository(penerbitService)
    }

    override val bukuRepository: BukuRepository by lazy {
        NetworkBukuRepository(bukuService)
    }

    override val kategoriRepository: KategoriRepository by lazy {
        NetworkKategoriRepository(kategoriService)
    }
}

interface AppContainer {
    val penulisRepository: PenulisRepository
    val penerbitRepository: PenerbitRepository
    val bukuRepository: BukuRepository
    val kategoriRepository: KategoriRepository
}

