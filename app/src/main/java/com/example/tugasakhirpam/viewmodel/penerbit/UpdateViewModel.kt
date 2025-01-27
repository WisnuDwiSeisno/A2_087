package com.example.tugasakhirpam.viewmodel.penerbit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.repository.BukuRepository
import com.example.tugasakhirpam.repository.PenerbitRepository
import com.example.tugasakhirpam.ui.view.buku.DestinasiUpdate
import com.example.tugasakhirpam.ui.view.penerbit.DestinasiUpdatePenerbit
import com.example.tugasakhirpam.viewmodel.penerbit.InsertPenerbitUiEvent
import com.example.tugasakhirpam.viewmodel.penerbit.InsertPenerbitUiState
import com.example.tugasakhirpam.viewmodel.penerbit.toInsertUiEvent
import com.example.tugasakhirpam.viewmodel.penerbit.toPenerbit
import kotlinx.coroutines.launch

class UpdatePenerbitViewModel(
    savedStateHandle: SavedStateHandle,
    private val penerbitRepository: PenerbitRepository
) : ViewModel() {

    var updateUiState by mutableStateOf(InsertPenerbitUiState())
        private set

    private val _idPenerbit: Int = checkNotNull(savedStateHandle[DestinasiUpdatePenerbit.ID_KATEGORI])

    init {
        // Mengambil data penerbit berdasarkan ID
        fetchPenerbitData()
    }

    // Fungsi untuk mengambil data penerbit berdasarkan ID
    private fun fetchPenerbitData() {
        viewModelScope.launch {
            try {
                val penerbit = penerbitRepository.getPenerbitById(_idPenerbit)
                updateUiState = updateUiState.copy(
                    insertUiEvent = penerbit.toInsertUiEvent()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk mengupdate penerbit
    fun updatePenerbitState(insertPenerbitUiEvent: InsertPenerbitUiEvent) {
        updateUiState = updateUiState.copy(insertUiEvent = insertPenerbitUiEvent)
    }

    // Fungsi untuk menyimpan update penerbit
    fun updatePenerbit() {
        viewModelScope.launch {
            try {
                penerbitRepository.updatePenerbit(
                    _idPenerbit,
                    updateUiState.insertUiEvent.toPenerbit()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
