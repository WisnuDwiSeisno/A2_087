package com.example.tugasakhirpam.ui.view.kategori

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import com.example.tugasakhirpam.R
import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.data.Kategori
import com.example.tugasakhirpam.navigation.DestinasiNavigasi
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.ui.view.buku.DestinasiDetail
import com.example.tugasakhirpam.ui.view.buku.OnError
import com.example.tugasakhirpam.ui.view.buku.OnLoading
import com.example.tugasakhirpam.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.viewmodel.buku.BukuUiState
import com.example.tugasakhirpam.viewmodel.buku.BukuViewModel
import com.example.tugasakhirpam.viewmodel.kategori.BukuByKategoriUiState
import com.example.tugasakhirpam.viewmodel.kategori.HomeViewModel
import com.example.tugasakhirpam.viewmodel.kategori.KategoriUiState

object DestinasiHomeKategoriByBuku : DestinasiNavigasi {
    override val route = "home_kategoribybuku"
    override val titleRes = "Home Kategoribuku"
}
@Composable
fun BukuByKategoriScreen(
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val kategoriUiState by viewModel.kategoriUiState
    val bukuByKategoriUiState by viewModel.bukuByKategoriUiState

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        // Bagian untuk menampilkan kategori
        Text(
            text = "Pilih Kategori",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        when (kategoriUiState) {
            is KategoriUiState.Loading -> CircularProgressIndicator()
            is KategoriUiState.Error -> Text(
                text = (kategoriUiState as KategoriUiState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
            is KategoriUiState.Success -> {
                val kategoriList = (kategoriUiState as KategoriUiState.Success).kategori
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(kategoriList) { kategori ->
                        Button(
                            onClick = { viewModel.fetchBukuByKategori(kategori.id_kategori) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(text = kategori.nama_kategori)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bagian untuk menampilkan buku berdasarkan kategori
        Text(
            text = "Daftar Buku",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        when (bukuByKategoriUiState) {
            is BukuByKategoriUiState.Loading -> CircularProgressIndicator()
            is BukuByKategoriUiState.Error -> Text(
                text = (bukuByKategoriUiState as BukuByKategoriUiState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
            is BukuByKategoriUiState.Success -> {
                val bukuList = (bukuByKategoriUiState as BukuByKategoriUiState.Success).bukuList
                if (bukuList.isEmpty()) {
                    Text(text = "Tidak ada buku pada kategori ini.")
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(bukuList) { buku ->
                            BukuCard(buku = buku, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BukuCard(
    buku: Buku,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = buku.nama_buku,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Deskripsi: ${buku.deskripsi_buku}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Tanggal Terbit: ${buku.tanggal_terbit}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
