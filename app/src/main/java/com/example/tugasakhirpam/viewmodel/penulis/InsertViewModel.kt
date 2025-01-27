package com.example.tugasakhirpam.viewmodel.penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.data.Penulis
import com.example.tugasakhirpam.repository.BukuRepository
import com.example.tugasakhirpam.repository.PenulisRepository
import kotlinx.coroutines.launch

class InsertPenulisViewModel(private val penulisRepository: PenulisRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPenulisUiState())
        private set

    fun updateInsertPenulisState(insertUiEvent: InsertPenulisUiEvent) {
        uiState = InsertPenulisUiState(insertUiEvent = insertUiEvent)
    }

    fun insertPenulis() {
        viewModelScope.launch {
            try {
                penulisRepository.createPenulis(uiState.insertUiEvent.toPenulis())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPenulisUiState(
    val insertUiEvent: InsertPenulisUiEvent = InsertPenulisUiEvent()
)

data class InsertPenulisUiEvent(
    val nama_penulis: String = "",
    val biografi: String = "",
    val kontak: String = "",
)

fun InsertPenulisUiEvent.toPenulis(): Penulis = Penulis(
    id_penulis = 0, // Assuming `id_penulis` is auto-generated
    nama_penulis = nama_penulis,
    biografi = biografi ,
    kontak = kontak
)

fun Penulis.toUiStatePenulis(): InsertPenulisUiState = InsertPenulisUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Penulis.toInsertUiEvent(): InsertPenulisUiEvent = InsertPenulisUiEvent(
    nama_penulis = nama_penulis,
    biografi = biografi ,
    kontak = kontak
)
