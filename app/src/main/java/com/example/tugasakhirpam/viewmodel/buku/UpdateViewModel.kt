package com.example.tugasakhirpam.viewmodel.buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.repository.BukuRepository
import com.example.tugasakhirpam.ui.view.buku.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateViewModel(savedStateHandle: SavedStateHandle, private val bukuRepository: BukuRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertUiState())
        private set

    private val _idBuku: Int = checkNotNull(savedStateHandle[DestinasiUpdate.ID_BUKU])

    init {
        // Mengambil data buku berdasarkan ID
        fetchBukuData()
        // Mengambil data dropdown kategori, penulis, penerbit
        fetchDropdownData()
    }

    // Fungsi untuk mengambil data buku berdasarkan ID
    private fun fetchBukuData() {
        viewModelScope.launch {
            try {
                val buku = bukuRepository.getBukuById(_idBuku)
                updateUiState = updateUiState.copy(
                    bukuUiEvent = buku.toBukuUiEvent() // Mengonversi Buku ke BukuUiEvent
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk mengupdate buku
    fun updateBukuState(bukuUiEvent: BukuUiEvent) {
        updateUiState = updateUiState.copy(bukuUiEvent = bukuUiEvent)
    }

    // Fungsi untuk menyimpan update buku
    fun updateBuku() {
        viewModelScope.launch {
            try {
                bukuRepository.updateBuku(_idBuku, updateUiState.bukuUiEvent.toBuku())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk mengambil data dropdown kategori, penulis, penerbit
    fun fetchDropdownData() {
        viewModelScope.launch {
            try {
                // Ambil data kategori, penulis, penerbit
                val kategoriList = bukuRepository.getKategoriList().map { DropdownItem(it.id_kategori, it.nama_kategori) }
                val penulisList = bukuRepository.getPenulisList().map { DropdownItem(it.id_penulis, it.nama_penulis) }
                val penerbitList = bukuRepository.getPenerbitList().map { DropdownItem(it.id_penerbit, it.nama_penerbit) }

                // Update UI state dengan data dropdown
                updateUiState = updateUiState.copy(
                    kategoriList = kategoriList,
                    penulisList = penulisList,
                    penerbitList = penerbitList
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

