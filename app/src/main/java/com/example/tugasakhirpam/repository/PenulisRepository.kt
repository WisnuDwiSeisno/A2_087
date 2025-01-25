package com.example.tugasakhirpam.repository

import com.example.tugasakhirpam.data.Penerbit
import com.example.tugasakhirpam.data.Penulis
import com.example.tugasakhirpam.services.PenulisService

class NetworkPenulisRepository(private val penulisService: PenulisService) : PenulisRepository {

    override suspend fun getPenulis(): List<Penulis> = penulisService.getAllPenulis()

    override suspend fun createPenulis(penulis: Penulis) {
        penulisService.createPenulis(penulis)
    }

    override suspend fun updatePenulis(id: Int, penulis: Penulis) {
        penulisService.updatePenulis(id, penulis)
    }

    override suspend fun deletePenulis(id: Int) {
        penulisService.deletePenulis(id)
    }

    override suspend fun getPenulisById(id: Int): Penulis = penulisService.getPenulisById(id)

}

interface PenulisRepository {
    suspend fun getPenulis(): List<Penulis>
    suspend fun getPenulisById(id: Int): Penulis
    suspend fun createPenulis(penulis: Penulis)
    suspend fun updatePenulis(id: Int, penulis: Penulis)
    suspend fun deletePenulis(id: Int)
}

