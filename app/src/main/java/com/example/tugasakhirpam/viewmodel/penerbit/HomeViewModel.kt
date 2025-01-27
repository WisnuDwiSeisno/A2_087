package com.example.tugasakhirpam.viewmodel.penerbit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.tugasakhirpam.data.Buku
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.data.Penerbit
import com.example.tugasakhirpam.repository.BukuRepository
import com.example.tugasakhirpam.repository.PenerbitRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
sealed class PenerbitUiState {
    data class Success(val penerbit: List<Penerbit>) : PenerbitUiState()
    object Error : PenerbitUiState()
    object Loading : PenerbitUiState()
}

class HomeViewModelPenerbit(private val penerbitRepository: PenerbitRepository) : ViewModel() {
    var penerbitUiState: PenerbitUiState by mutableStateOf(PenerbitUiState.Loading)
        private set

    init {
        getPenerbitList()
    }

    fun getPenerbitList() {
        viewModelScope.launch {
            penerbitUiState = PenerbitUiState.Loading
            penerbitUiState = try {
                val penerbitList = penerbitRepository.getPenerbit()
                PenerbitUiState.Success(penerbitList)
            } catch (e: IOException) {
                PenerbitUiState.Error
            } catch (e: HttpException) {
                PenerbitUiState.Error
            }
        }
    }

    fun deletePenerbit(id: Int) {
        viewModelScope.launch {
            try {
                penerbitRepository.deletePenerbit(id)
                getPenerbitList() // Refresh the list after deletion
            } catch (e: IOException) {
                penerbitUiState = PenerbitUiState.Error
            } catch (e: HttpException) {
                penerbitUiState = PenerbitUiState.Error
            }
        }
    }

    fun createPenerbit(penerbit: Penerbit) {
        viewModelScope.launch {
            try {
                penerbitRepository.createPenerbit(penerbit)
                getPenerbitList() // Refresh the list after creation
            } catch (e: IOException) {
                penerbitUiState = PenerbitUiState.Error
            } catch (e: HttpException) {
                penerbitUiState = PenerbitUiState.Error
            }
        }
    }

    fun updatePenerbit(id: Int, penerbit: Penerbit) {
        viewModelScope.launch {
            try {
                penerbitRepository.updatePenerbit(id, penerbit)
                getPenerbitList() // Refresh the list after updating
            } catch (e: IOException) {
                penerbitUiState = PenerbitUiState.Error
            } catch (e: HttpException) {
                penerbitUiState = PenerbitUiState.Error
            }
        }
    }
}
