package com.example.tugasakhirpam.viewmodel.buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.repository.BukuRepository
import kotlinx.coroutines.launch

class DetailBukuViewModel(private val bukuRepository: BukuRepository) : ViewModel() {

    var uiState by mutableStateOf(DetailBukuUiState())
        private set

    fun fetchDetailBuku(id: Int) {
        viewModelScope.launch {
            uiState = DetailBukuUiState(isLoading = true)
            try {
                val buku = bukuRepository.getBukuById(id)
                uiState = DetailBukuUiState(detailUiEvent = buku.toDetailUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailBukuUiState(
                    isError = true,
                    errorMessage = "Failed to fetch details: ${e.message}"
                )
            }
        }
    }
}

data class DetailBukuUiState(
    val detailUiEvent: DetailUiEvent = DetailUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != DetailUiEvent()
}

data class DetailUiEvent(
    val id_buku: Int = 0,
    val nama_buku: String = "",
    val deskripsi_buku: String = "",
    val tanggal_terbit: String = "",
    val status_buku: String = "",
    val id_kategori: Int = 0,
    val id_penulis: Int = 0,
    val id_penerbit: Int = 0
)

fun Buku.toDetailUiEvent(): DetailUiEvent {
    return DetailUiEvent(
        id_buku = id_buku,
        nama_buku = nama_buku,
        deskripsi_buku = deskripsi_buku,
        tanggal_terbit = tanggal_terbit,
        status_buku = status_buku,
        id_kategori = id_kategori,
        id_penulis = id_penulis,
        id_penerbit = id_penerbit
    )
}
