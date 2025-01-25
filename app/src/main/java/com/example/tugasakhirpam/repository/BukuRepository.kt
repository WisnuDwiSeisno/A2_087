package com.example.tugasakhirpam.repository

import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.data.Kategori
import com.example.tugasakhirpam.data.Penerbit
import com.example.tugasakhirpam.data.Penulis
import com.example.tugasakhirpam.services.BukuService

class NetworkBukuRepository(private val bukuService: BukuService) : BukuRepository {

    override suspend fun getBuku(): List<Buku> {
        return bukuService.getBuku()
    }

    // Implementasi untuk kategori, penulis, dan penerbit
    override suspend fun getKategoriList(): List<Kategori> {
        return bukuService.getKategoriList()
    }

    override suspend fun getPenulisList(): List<Penulis> {
        return bukuService.getPenulisList()
    }

    override suspend fun getPenerbitList(): List<Penerbit> {
        return bukuService.getPenerbitList()
    }

    override suspend fun getBukuById(id: Int): Buku = bukuService.getBukuById(id)

    override suspend fun createBuku(buku: Buku) {
        bukuService.createBuku(buku)
    }

    override suspend fun updateBuku(id: Int, buku: Buku) {
        bukuService.updateBuku(id, buku)
    }

    override suspend fun deleteBuku(id: Int) {
        bukuService.deleteBuku(id)
    }
}

interface BukuRepository {
    suspend fun getBuku(): List<Buku>
    suspend fun getBukuById(id: Int): Buku
    suspend fun createBuku(buku: Buku)
    suspend fun updateBuku(id: Int, buku: Buku)
    suspend fun deleteBuku(id: Int)
    // Tambahkan metode untuk mendapatkan kategori, penulis, dan penerbit
    suspend fun getKategoriList(): List<Kategori>
    suspend fun getPenulisList(): List<Penulis>
    suspend fun getPenerbitList(): List<Penerbit>
}

