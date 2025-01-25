package com.example.tugasakhirpam.services

import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.data.Kategori
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface KategoriService {
    @GET("kategori")
    suspend fun getAllKategori(): List<Kategori>

    @GET("kategori/{id}")
    suspend fun getKategoriById(@Path("id") id: Int): Kategori

    @POST("kategori")
    suspend fun createKategori(@Body kategori: Kategori): Response<Unit>

    @PUT("kategori/{id}")
    suspend fun updateKategori(@Path("id") id: Int, @Body kategori: Kategori): Response<Unit>

    @DELETE("kategori/{id}")
    suspend fun deleteKategori(@Path("id") id: Int): Response<Unit>

    @GET("kategori/buku/{id_buku}")
    suspend fun getKategoriByBukuId(@Path("id_buku") idBuku: Int): Kategori

    @GET("kategori/{id_kategori}/buku")
    suspend fun getBukuByKategoriId(@Path("id_kategori") idKategori: Int): List<Buku>
}
