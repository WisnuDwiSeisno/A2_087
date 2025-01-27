package com.example.tugasakhirpam.viewmodel.kategori

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.tugasakhirpam.data.Buku
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.repository.BukuRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.data.Kategori
import com.example.tugasakhirpam.repository.KategoriRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(private val kategoriRepository: KategoriRepository) : ViewModel() {

    // UI State untuk kategori
    private val _kategoriUiState: MutableState<KategoriUiState> = mutableStateOf(KategoriUiState.Loading)
    val kategoriUiState: State<KategoriUiState> = _kategoriUiState

    // UI State untuk buku dalam kategori tertentu
    private val _bukuByKategoriUiState: MutableState<BukuByKategoriUiState> = mutableStateOf(BukuByKategoriUiState.Loading)
    val bukuByKategoriUiState: State<BukuByKategoriUiState> = _bukuByKategoriUiState

    init {
        fetchKategoriList()  // Mendapatkan kategori saat ViewModel pertama kali diinisialisasi
    }

    /**
     * Mendapatkan daftar kategori.
     */
    fun fetchKategoriList() {
        viewModelScope.launch {
            _kategoriUiState.value = KategoriUiState.Loading
            try {
                val kategoriList = kategoriRepository.getKategori()
                _kategoriUiState.value = KategoriUiState.Success(kategoriList)
            } catch (e: IOException) {
                _kategoriUiState.value = KategoriUiState.Error("Network error, please try again.")
            } catch (e: HttpException) {
                _kategoriUiState.value = KategoriUiState.Error("Failed to load data.")
            }
        }
    }

    /**
     * Mendapatkan buku berdasarkan kategori ID.
     */
    fun fetchBukuByKategori(idKategori: Int) {
        viewModelScope.launch {
            _bukuByKategoriUiState.value = BukuByKategoriUiState.Loading
            try {
                val bukuList = kategoriRepository.getBukuByKategoriId(idKategori)
                _bukuByKategoriUiState.value = BukuByKategoriUiState.Success(bukuList)
            } catch (e: IOException) {
                _bukuByKategoriUiState.value = BukuByKategoriUiState.Error("Network error, please try again.")
            } catch (e: HttpException) {
                _bukuByKategoriUiState.value = BukuByKategoriUiState.Error("Failed to load data.")
            }
        }
    }
}

/**
 * UI State untuk kategori
 */
sealed class KategoriUiState {
    object Loading : KategoriUiState()
    data class Success(val kategori: List<Kategori>) : KategoriUiState()
    data class Error(val message: String) : KategoriUiState()
}

/**
 * UI State untuk buku dalam kategori tertentu
 */
sealed class BukuByKategoriUiState {
    object Loading : BukuByKategoriUiState()
    data class Success(val bukuList: List<Buku>) : BukuByKategoriUiState()
    data class Error(val message: String) : BukuByKategoriUiState()
}
