package com.example.tugasakhirpam.viewmodel.kategori


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.repository.BukuRepository
import com.example.tugasakhirpam.repository.KategoriRepository
import com.example.tugasakhirpam.ui.view.buku.DestinasiUpdate
import com.example.tugasakhirpam.ui.view.kategori.DestinasiUpdateKategori
import kotlinx.coroutines.launch

class UpdateKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertKategoriUiState())
        private set

    private val _idKategori: Int = checkNotNull(savedStateHandle[DestinasiUpdateKategori.ID_KATEGORI])

    init {
        // Mengambil data kategori berdasarkan ID
        fetchKategoriData()
    }

    // Fungsi untuk mengambil data kategori berdasarkan ID
    private fun fetchKategoriData() {
        viewModelScope.launch {
            try {
                val kategori = kategoriRepository.getKategoriById(_idKategori)
                updateUiState = updateUiState.copy(
                    insertUiEvent = kategori.toInsertUiEvent()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk mengupdate kategori
    fun updateKategoriState(insertKategoriUiEvent: InsertKategoriUiEvent) {
        updateUiState = updateUiState.copy(insertUiEvent = insertKategoriUiEvent)
    }

    // Fungsi untuk menyimpan update kategori
    fun updateKategori() {
        viewModelScope.launch {
            try {
                kategoriRepository.updateKategori(
                    _idKategori,
                    updateUiState.insertUiEvent.toKategori()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
