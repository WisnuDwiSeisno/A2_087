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

class DetailPenulisViewModel(private val penulisRepository: PenulisRepository) : ViewModel() {

    var uiState by mutableStateOf(DetailPenulisUiState())
        private set

    // Ambil detail penulis berdasarkan ID
    fun fetchDetailPenulis(id: Int) {
        viewModelScope.launch {
            uiState = DetailPenulisUiState(isLoading = true)
            try {
                val penulis = penulisRepository.getPenulisById(id)
                uiState = DetailPenulisUiState(detailUiEvent = penulis.toDetailUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailPenulisUiState(
                    isError = true,
                    errorMessage = "Failed to fetch details: ${e.message}"
                )
            }
        }
    }
}

data class DetailPenulisUiState(
    val detailUiEvent: DetailUiEvent = DetailUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != DetailUiEvent()
}

data class DetailUiEvent(
    val id_penulis: Int = 0,
    val nama_penulis: String = "",
    val biografi: String = "",
    val kontak: String = "",
)

fun Penulis.toDetailUiEvent(): DetailUiEvent {
    return DetailUiEvent(
        id_penulis = id_penulis,
        nama_penulis = nama_penulis,
        biografi = biografi,
        kontak = kontak,
    )
}
