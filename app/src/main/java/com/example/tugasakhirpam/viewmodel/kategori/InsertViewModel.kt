package com.example.tugasakhirpam.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.data.Kategori
import com.example.tugasakhirpam.repository.BukuRepository
import com.example.tugasakhirpam.repository.KategoriRepository
import kotlinx.coroutines.launch
class InsertKategoriViewModel(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertKategoriUiState())
        private set

    fun updateInsertKategoriState(insertUiEvent: InsertKategoriUiEvent) {
        uiState = InsertKategoriUiState(insertUiEvent = insertUiEvent)
    }

    fun insertKategori() {
        viewModelScope.launch {
            try {
                kategoriRepository.createKategori(uiState.insertUiEvent.toKategori())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertKategoriUiState(
    val insertUiEvent: InsertKategoriUiEvent = InsertKategoriUiEvent()
)

data class InsertKategoriUiEvent(
    val nama_kategori: String = "",
    val deskripsi_kategori: String = ""
)

fun InsertKategoriUiEvent.toKategori(): Kategori = Kategori(
    id_kategori = 0, // Assuming `id_kategori` is auto-generated
    nama_kategori = nama_kategori,
    deskripsi_kategori = deskripsi_kategori
)

fun Kategori.toUiStateKategori(): InsertKategoriUiState = InsertKategoriUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Kategori.toInsertUiEvent(): InsertKategoriUiEvent = InsertKategoriUiEvent(
    nama_kategori = nama_kategori,
    deskripsi_kategori = deskripsi_kategori
)
