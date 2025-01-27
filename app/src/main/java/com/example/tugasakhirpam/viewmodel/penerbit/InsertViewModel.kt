package com.example.tugasakhirpam.viewmodel.penerbit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.data.Penerbit
import com.example.tugasakhirpam.repository.BukuRepository
import com.example.tugasakhirpam.repository.PenerbitRepository
import kotlinx.coroutines.launch

class InsertPenerbitViewModel(private val penerbitRepository: PenerbitRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPenerbitUiState())
        private set

    fun updateInsertPenerbitState(insertUiEvent: InsertPenerbitUiEvent) {
        uiState = InsertPenerbitUiState(insertUiEvent = insertUiEvent)
    }

    fun insertPenerbit() {
        viewModelScope.launch {
            try {
                penerbitRepository.createPenerbit(uiState.insertUiEvent.toPenerbit())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPenerbitUiState(
    val insertUiEvent: InsertPenerbitUiEvent = InsertPenerbitUiEvent()
)

data class InsertPenerbitUiEvent(
    val nama_penerbit: String = "",
    val alamat_penerbit: String = "",
    val telepon_penerbit: String = ""
)

fun InsertPenerbitUiEvent.toPenerbit(): Penerbit = Penerbit(
    id_penerbit = 0, // Assuming `id_penerbit` is auto-generated
    nama_penerbit = nama_penerbit,
    alamat_penerbit = alamat_penerbit,
    telepon_penerbit = telepon_penerbit
)

fun Penerbit.toUiStatePenerbit(): InsertPenerbitUiState = InsertPenerbitUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Penerbit.toInsertUiEvent(): InsertPenerbitUiEvent = InsertPenerbitUiEvent(
    nama_penerbit = nama_penerbit,
    alamat_penerbit = alamat_penerbit,
    telepon_penerbit = telepon_penerbit
)
