package com.example.tugasakhirpam.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.data.Kategori
import com.example.tugasakhirpam.repository.KategoriRepository
import kotlinx.coroutines.launch

class DetailKategoriViewModel(private val kategoriRepository: KategoriRepository) : ViewModel() {

    var uiState by mutableStateOf(DetailKategoriUiState())
        private set

    // Ambil detail kategori berdasarkan ID
    fun fetchDetailKategori(id: Int) {
        viewModelScope.launch {
            uiState = DetailKategoriUiState(isLoading = true)
            try {
                val kategori = kategoriRepository.getKategoriById(id)
                uiState = DetailKategoriUiState(detailUiEvent = kategori.toDetailUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailKategoriUiState(
                    isError = true,
                    errorMessage = "Failed to fetch details: ${e.message}"
                )
            }
        }
    }
}

data class DetailKategoriUiState(
    val detailUiEvent: DetailUiEvent = DetailUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != DetailUiEvent()
}

data class DetailUiEvent(
    val id_kategori: Int = 0,
    val nama_kategori: String = "",
    val deskripsi_kategori: String = ""
)

fun Kategori.toDetailUiEvent(): DetailUiEvent {
    return DetailUiEvent(
        id_kategori = id_kategori,
        nama_kategori = nama_kategori,
        deskripsi_kategori = deskripsi_kategori
    )
}
