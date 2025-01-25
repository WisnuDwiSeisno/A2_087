package com.example.tugasakhirpam.network

import com.example.tugasakhirpam.data.Buku
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("buku")
    suspend fun getAllBooks(): List<Buku>

    @POST("buku")
    suspend fun createBook(@Body buku: Buku): Response<Void>

    @PUT("buku/{id}")
    suspend fun updateBook(@Path("id") id: Int, @Body buku: Buku): Response<Void>

    @DELETE("buku/{id}")
    suspend fun deleteBook(@Path("id") id: Int): Response<Void>
}
