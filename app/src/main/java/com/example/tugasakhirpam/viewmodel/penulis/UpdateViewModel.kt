package com.example.tugasakhirpam.viewmodel.penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.repository.BukuRepository
import com.example.tugasakhirpam.repository.PenulisRepository
import com.example.tugasakhirpam.ui.view.buku.DestinasiUpdate
import com.example.tugasakhirpam.ui.view.penulis.DestinasiUpdatePenulis
import com.example.tugasakhirpam.viewmodel.penulis.InsertPenulisUiEvent
import com.example.tugasakhirpam.viewmodel.penulis.InsertPenulisUiState
import com.example.tugasakhirpam.viewmodel.penulis.toInsertUiEvent
import com.example.tugasakhirpam.viewmodel.penulis.toPenulis
import kotlinx.coroutines.launch

class UpdatePenulisViewModel(
    savedStateHandle: SavedStateHandle,
    private val penulisRepository: PenulisRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertPenulisUiState())
        private set

    private val _idPenulis: Int = checkNotNull(savedStateHandle[DestinasiUpdatePenulis.ID_KATEGORI])

    init {
        // Mengambil data penulis berdasarkan ID
        fetchPenulisData()
    }

    // Fungsi untuk mengambil data penulis berdasarkan ID
    private fun fetchPenulisData() {
        viewModelScope.launch {
            try {
                val penulis = penulisRepository.getPenulisById(_idPenulis)
                updateUiState = updateUiState.copy(
                    insertUiEvent = penulis.toInsertUiEvent()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk mengupdate penulis
    fun updatePenulisState(insertPenulisUiEvent: InsertPenulisUiEvent) {
        updateUiState = updateUiState.copy(insertUiEvent = insertPenulisUiEvent)
    }

    // Fungsi untuk menyimpan update penulis
    fun updatePenulis() {
        viewModelScope.launch {
            try {
                penulisRepository.updatePenulis(
                    _idPenulis,
                    updateUiState.insertUiEvent.toPenulis()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
