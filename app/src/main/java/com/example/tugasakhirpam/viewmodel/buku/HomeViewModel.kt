package com.example.tugasakhirpam.viewmodel.buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.tugasakhirpam.data.Buku
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.repository.BukuRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BukuViewModel(private val bukuRepository: BukuRepository) : ViewModel() {

    var bukuUiState: BukuUiState by mutableStateOf(BukuUiState.Loading)
        private set

    init {
        fetchBukuList()
    }

    // Mengambil daftar buku dari repository
    fun fetchBukuList() {
        viewModelScope.launch {
            bukuUiState = BukuUiState.Loading
            bukuUiState = try {
                BukuUiState.Success(bukuRepository.getBuku())
            } catch (e: IOException) {
                BukuUiState.Error
            } catch (e: HttpException) {
                BukuUiState.Error
            }
        }
    }

    // Menghapus buku berdasarkan ID
    fun deleteBuku(id: Int) {
        viewModelScope.launch {
            try {
                bukuRepository.deleteBuku(id)
                fetchBukuList() // Refresh daftar buku
            } catch (e: IOException) {
                bukuUiState = BukuUiState.Error
            } catch (e: HttpException) {
                bukuUiState = BukuUiState.Error
            }
        }
    }

    // Menambahkan buku baru
    fun createBuku(buku: Buku) {
        viewModelScope.launch {
            try {
                bukuRepository.createBuku(buku)
                fetchBukuList() // Refresh daftar buku
            } catch (e: IOException) {
                bukuUiState = BukuUiState.Error
            } catch (e: HttpException) {
                bukuUiState = BukuUiState.Error
            }
        }
    }
}


sealed class BukuUiState {
    data class Success(val buku: List<Buku>) : BukuUiState()
    object Error : BukuUiState()
    object Loading : BukuUiState()
}
