package com.example.tugasakhirpam.services

import com.example.tugasakhirpam.data.Kategori
import com.example.tugasakhirpam.data.Penerbit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenerbitService {
    @GET("penerbit")
    suspend fun getAllPenerbit(): List<Penerbit>

    @GET("penerbit/{id}")
    suspend fun getPenerbitById(@Path("id") id: Int): Penerbit

    @POST("penerbit")
    suspend fun createPenerbit(@Body penerbit: Penerbit): Response<Unit>

    @PUT("penerbit/{id}")
    suspend fun updatePenerbit(@Path("id") id: Int, @Body penerbit: Penerbit): Response<Unit>

    @DELETE("penerbit/{id}")
    suspend fun deletePenerbit(@Path("id") id: Int): Response<Unit>
}
