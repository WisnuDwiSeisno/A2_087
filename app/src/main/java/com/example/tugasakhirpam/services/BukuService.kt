package com.example.tugasakhirpam.services

import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.data.Kategori
import com.example.tugasakhirpam.data.Penerbit
import com.example.tugasakhirpam.data.Penulis
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface BukuService {
    @GET("buku")
    suspend fun getBuku(): List<Buku>

    @GET("buku/{id}")
    suspend fun getBukuById(@Path("id") id: Int): Buku

    @POST("buku")
    suspend fun createBuku(@Body buku: Buku): Response<Unit>

    @PUT("buku/{id}")
    suspend fun updateBuku(@Path("id") id: Int, @Body buku: Buku): Response<Unit>

    @DELETE("buku/{id}")
    suspend fun deleteBuku(@Path("id") id: Int): Response<Unit>

    // Dropdown
    @GET("kategori")
    suspend fun getKategoriList(): List<Kategori>

    @GET("penulis")
    suspend fun getPenulisList(): List<Penulis>

    @GET("penerbit")
    suspend fun getPenerbitList(): List<Penerbit>
}