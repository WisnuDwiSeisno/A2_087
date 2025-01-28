package com.example.tugasakhirpam.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tugasakhirpam.PerpustakaanApplication
import com.example.tugasakhirpam.viewmodel.buku.BukuViewModel
import com.example.tugasakhirpam.viewmodel.buku.DetailBukuViewModel
import com.example.tugasakhirpam.viewmodel.buku.InsertViewModel
import com.example.tugasakhirpam.viewmodel.buku.UpdateViewModel
import com.example.tugasakhirpam.viewmodel.kategori.DetailKategoriViewModel
import com.example.tugasakhirpam.viewmodel.kategori.HomeViewModel
import com.example.tugasakhirpam.viewmodel.kategori.InsertKategoriViewModel
import com.example.tugasakhirpam.viewmodel.kategori.UpdateKategoriViewModel
import com.example.tugasakhirpam.viewmodel.penerbit.DetailPenerbitViewModel
import com.example.tugasakhirpam.viewmodel.penerbit.HomeViewModelPenerbit
import com.example.tugasakhirpam.viewmodel.penerbit.InsertPenerbitViewModel
import com.example.tugasakhirpam.viewmodel.penerbit.UpdatePenerbitViewModel
import com.example.tugasakhirpam.viewmodel.penulis.DetailPenulisViewModel
import com.example.tugasakhirpam.viewmodel.penulis.HomeViewModelPenulis
import com.example.tugasakhirpam.viewmodel.penulis.InsertPenulisViewModel
import com.example.tugasakhirpam.viewmodel.penulis.UpdatePenulisViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // BukuViewModel
        initializer {
            BukuViewModel(aplikasiPerpustakaan().container.bukuRepository)
        }
        initializer {
            DetailBukuViewModel(aplikasiPerpustakaan().container.bukuRepository)
        }
        initializer {
            UpdateViewModel(
                createSavedStateHandle(),
                aplikasiPerpustakaan().container.bukuRepository
            )
        }
        initializer {
            InsertViewModel(aplikasiPerpustakaan().container.bukuRepository)
        }
        // PenulisViewModel
        initializer {
            HomeViewModelPenulis(aplikasiPerpustakaan().container.penulisRepository)
        }
        initializer {
            InsertPenulisViewModel(aplikasiPerpustakaan().container.penulisRepository)
        }
        initializer {
            DetailPenulisViewModel(aplikasiPerpustakaan().container.penulisRepository)
        }
        initializer {
            UpdatePenulisViewModel(
                createSavedStateHandle(),
                aplikasiPerpustakaan().container.penulisRepository
            )
        }

        // PenerbitViewModel
        initializer {
            HomeViewModelPenerbit(aplikasiPerpustakaan().container.penerbitRepository)
        }
        initializer {
            InsertPenerbitViewModel(aplikasiPerpustakaan().container.penerbitRepository)
        }
        initializer {
            DetailPenerbitViewModel(aplikasiPerpustakaan().container.penerbitRepository)
        }
        initializer {
            UpdatePenerbitViewModel(
                createSavedStateHandle(),
                aplikasiPerpustakaan().container.penerbitRepository
            )
        }


        // KategoriViewModel
        initializer {
            HomeViewModel(aplikasiPerpustakaan().container.kategoriRepository)
        }
        initializer {
            DetailKategoriViewModel(aplikasiPerpustakaan().container.kategoriRepository)
        }
        initializer {
            InsertKategoriViewModel(aplikasiPerpustakaan().container.kategoriRepository)
        }
        initializer {
            UpdateKategoriViewModel(
                createSavedStateHandle(),
                aplikasiPerpustakaan().container.kategoriRepository
            )
        }
    }
}

fun CreationExtras.aplikasiPerpustakaan(): PerpustakaanApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PerpustakaanApplication)
