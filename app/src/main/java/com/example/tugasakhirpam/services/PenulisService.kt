package com.example.tugasakhirpam.services

import com.example.tugasakhirpam.data.Penerbit
import com.example.tugasakhirpam.data.Penulis
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenulisService {
    @GET("penulis")
    suspend fun getAllPenulis(): List<Penulis>

    @GET("penulis/{id}")
    suspend fun getPenulisById(@Path("id") id: Int): Penulis

    @POST("penulis")
    suspend fun createPenulis(@Body penulis: Penulis): Response<Unit>

    @PUT("penulis/{id}")
    suspend fun updatePenulis(@Path("id") id: Int, @Body penulis: Penulis): Response<Unit>

    @DELETE("penulis/{id}")
    suspend fun deletePenulis(@Path("id") id: Int): Response<Unit>
}
