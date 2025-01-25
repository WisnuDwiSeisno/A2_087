package com.example.tugasakhirpam.repository

import com.example.tugasakhirpam.data.Kategori
import com.example.tugasakhirpam.data.Penerbit
import com.example.tugasakhirpam.services.PenerbitService

class NetworkPenerbitRepository(private val penerbitService: PenerbitService) : PenerbitRepository {

    override suspend fun getPenerbit(): List<Penerbit> = penerbitService.getAllPenerbit()

    override suspend fun createPenerbit(penerbit: Penerbit) {
        penerbitService.createPenerbit(penerbit)
    }

    override suspend fun updatePenerbit(id: Int, penerbit: Penerbit) {
        penerbitService.updatePenerbit(id, penerbit)
    }

    override suspend fun deletePenerbit(id: Int) {
        penerbitService.deletePenerbit(id)
    }

    override suspend fun getPenerbitById(id: Int): Penerbit = penerbitService.getPenerbitById(id)
}

interface PenerbitRepository {
    suspend fun getPenerbit(): List<Penerbit>
    suspend fun getPenerbitById(id: Int): Penerbit
    suspend fun createPenerbit(penerbit: Penerbit)
    suspend fun updatePenerbit(id: Int, penerbit: Penerbit)
    suspend fun deletePenerbit(id: Int)
}
