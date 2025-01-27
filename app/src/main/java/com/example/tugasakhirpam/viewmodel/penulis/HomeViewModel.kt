package com.example.tugasakhirpam.viewmodel.penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.tugasakhirpam.data.Buku
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.data.Penulis
import com.example.tugasakhirpam.repository.BukuRepository
import com.example.tugasakhirpam.repository.PenulisRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
sealed class PenulisUiState {
    data class Success(val penulis: List<Penulis>) : PenulisUiState()
    object Error : PenulisUiState()
    object Loading : PenulisUiState()
}

class HomeViewModelPenulis(private val penulisRepository: PenulisRepository) : ViewModel() {
    var penulisUiState: PenulisUiState by mutableStateOf(PenulisUiState.Loading)
        private set

    init {
        getPenulisList()
    }

    fun getPenulisList() {
        viewModelScope.launch {
            penulisUiState = PenulisUiState.Loading
            penulisUiState = try {
                val penulisList = penulisRepository.getPenulis()
                PenulisUiState.Success(penulisList)
            } catch (e: IOException) {
                PenulisUiState.Error
            } catch (e: HttpException) {
                PenulisUiState.Error
            }
        }
    }

    fun deletePenulis(id: Int) {
        viewModelScope.launch {
            try {
                penulisRepository.deletePenulis(id)
                getPenulisList() // Refresh the list after deletion
            } catch (e: IOException) {
                penulisUiState = PenulisUiState.Error
            } catch (e: HttpException) {
                penulisUiState = PenulisUiState.Error
            }
        }
    }

    fun createPenulis(penulis: Penulis) {
        viewModelScope.launch {
            try {
                penulisRepository.createPenulis(penulis)
                getPenulisList() // Refresh the list after creation
            } catch (e: IOException) {
                penulisUiState = PenulisUiState.Error
            } catch (e: HttpException) {
                penulisUiState = PenulisUiState.Error
            }
        }
    }

    fun updatePenulis(id: Int, penulis: Penulis) {
        viewModelScope.launch {
            try {
                penulisRepository.updatePenulis(id, penulis)
                getPenulisList() // Refresh the list after updating
            } catch (e: IOException) {
                penulisUiState = PenulisUiState.Error
            } catch (e: HttpException) {
                penulisUiState = PenulisUiState.Error
            }
        }
    }
}
