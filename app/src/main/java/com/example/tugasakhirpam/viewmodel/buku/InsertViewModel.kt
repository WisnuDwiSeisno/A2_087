package com.example.tugasakhirpam.viewmodel.buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.repository.BukuRepository
import kotlinx.coroutines.launch


class InsertViewModel(private val bukuRepository: BukuRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateBukuState(bukuUiEvent: BukuUiEvent) {
        uiState = InsertUiState(bukuUiEvent = bukuUiEvent)
    }

    var kategoriList by mutableStateOf<List<DropdownItem>>(emptyList())
        private set
    var penulisList by mutableStateOf<List<DropdownItem>>(emptyList())
        private set
    var penerbitList by mutableStateOf<List<DropdownItem>>(emptyList())
        private set

    // Fetch dropdown data when the screen is initialized
    suspend fun fetchDropdownData() {
        try {
            kategoriList = bukuRepository.getKategoriList().map { DropdownItem(it.id_kategori, it.nama_kategori) }
            penulisList = bukuRepository.getPenulisList().map { DropdownItem(it.id_penulis, it.nama_penulis) }
            penerbitList = bukuRepository.getPenerbitList().map { DropdownItem(it.id_penerbit, it.nama_penerbit) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun insertBuku() {
        viewModelScope.launch {
            try {
                bukuRepository.createBuku(uiState.bukuUiEvent.toBuku())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun updateBuku(id: Int) {
        viewModelScope.launch {
            try {
                bukuRepository.updateBuku(id, uiState.bukuUiEvent.toBuku())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun deleteBuku(id: Int) {
        viewModelScope.launch {
            try {
                bukuRepository.deleteBuku(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getBukuById(id: Int) {
        viewModelScope.launch {
            try {
                val buku = bukuRepository.getBukuById(id)
                uiState = InsertUiState(bukuUiEvent = buku.toBukuUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getAllBuku() {
        viewModelScope.launch {
            try {
                val bukuList = bukuRepository.getBuku()
                uiState = InsertUiState(bukuUiEventList = bukuList.map { it.toBukuUiEvent() })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val bukuUiEvent: BukuUiEvent = BukuUiEvent(),
    val bukuUiEventList: List<BukuUiEvent> = listOf(),
    val kategoriList: List<DropdownItem> = listOf(), // Menambahkan kategoriList
    val penulisList: List<DropdownItem> = listOf(),  // Menambahkan penulisList
    val penerbitList: List<DropdownItem> = listOf()   // Menambahkan penerbitList
)


data class BukuUiEvent(
    val id_buku: Int = 0,
    val nama_buku: String = "",
    val deskripsi_buku: String = "",
    val tanggal_terbit: String = "",
    val status_buku: String = "",
    val id_kategori: Int = 0,
    val id_penulis: Int = 0,
    val id_penerbit: Int = 0
)

fun BukuUiEvent.toBuku(): Buku = Buku(
    id_buku = id_buku,
    nama_buku = nama_buku,
    deskripsi_buku = deskripsi_buku,
    tanggal_terbit = tanggal_terbit,
    status_buku = status_buku,
    id_kategori = id_kategori,
    id_penulis = id_penulis,
    id_penerbit = id_penerbit
)

fun Buku.toBukuUiEvent(): BukuUiEvent = BukuUiEvent(
    id_buku = id_buku,
    nama_buku = nama_buku,
    deskripsi_buku = deskripsi_buku,
    tanggal_terbit = tanggal_terbit,
    status_buku = status_buku,
    id_kategori = id_kategori,
    id_penulis = id_penulis,
    id_penerbit = id_penerbit
)

data class DropdownItem(
    val id: Int,
    val name: String
)

