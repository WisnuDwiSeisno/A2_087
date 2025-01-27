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

class DetailPenerbitViewModel(private val penerbitRepository: PenerbitRepository) : ViewModel() {

    var uiState by mutableStateOf(DetailPenerbitUiState())
        private set

    // Ambil detail penerbit berdasarkan ID
    fun fetchDetailPenerbit(id: Int) {
        viewModelScope.launch {
            uiState = DetailPenerbitUiState(isLoading = true)
            try {
                val penerbit = penerbitRepository.getPenerbitById(id)
                uiState = DetailPenerbitUiState(detailUiEvent = penerbit.toDetailUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = DetailPenerbitUiState(
                    isError = true,
                    errorMessage = "Failed to fetch details: ${e.message}"
                )
            }
        }
    }
}

data class DetailPenerbitUiState(
    val detailUiEvent: DetailUiEvent = DetailUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != DetailUiEvent()
}

data class DetailUiEvent(
    val id_penerbit: Int = 0,
    val nama_penerbit: String = "",
    val alamat_penerbit: String = "",
    val telepon_penerbit: String = "",
)

fun Penerbit.toDetailUiEvent(): DetailUiEvent {
    return DetailUiEvent(
        id_penerbit = id_penerbit,
        nama_penerbit = nama_penerbit,
        alamat_penerbit = alamat_penerbit,
        telepon_penerbit = telepon_penerbit,
    )
}
