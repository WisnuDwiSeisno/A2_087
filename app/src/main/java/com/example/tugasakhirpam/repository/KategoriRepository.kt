package com.example.tugasakhirpam.repository

import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.data.Kategori
import com.example.tugasakhirpam.services.KategoriService

class NetworkKategoriRepository(private val kategoriService: KategoriService) : KategoriRepository {

    override suspend fun getKategori(): List<Kategori> = kategoriService.getAllKategori()

    override suspend fun getKategoriById(id: Int): Kategori = kategoriService.getKategoriById(id)

    override suspend fun createKategori(kategori: Kategori) {
        kategoriService.createKategori(kategori)
    }

    override suspend fun updateKategori(id: Int, kategori: Kategori) {
        kategoriService.updateKategori(id, kategori)
    }

    override suspend fun deleteKategori(id: Int) {
        kategoriService.deleteKategori(id)
    }

    override suspend fun getBukuByKategoriId(idKategori: Int): List<Buku> {
        return kategoriService.getBukuByKategoriId(idKategori)
    }
    override suspend fun getKategoriByBukuId(idBuku: Int): Kategori {
        return kategoriService.getKategoriByBukuId(idBuku)
    }

}

interface KategoriRepository {
    suspend fun getBukuByKategoriId(idKategori: Int): List<Buku>
    suspend fun getKategoriByBukuId(idBuku: Int): Kategori
    suspend fun getKategori(): List<Kategori>
    suspend fun getKategoriById(id: Int): Kategori
    suspend fun createKategori(kategori: Kategori)
    suspend fun updateKategori(id: Int, kategori: Kategori)
    suspend fun deleteKategori(id: Int)
}
