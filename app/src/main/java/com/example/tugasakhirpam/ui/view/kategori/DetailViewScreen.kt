package com.example.tugasakhirpam.ui.view.kategori

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.tugasakhirpam.navigation.DestinasiNavigasi
import com.example.tugasakhirpam.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.viewmodel.buku.DetailBukuViewModel
import com.example.tugasakhirpam.viewmodel.kategori.DetailKategoriViewModel

object DestinasiDetailKategori : DestinasiNavigasi {
    override val route = "detail_kategori/{id}"
    override val titleRes = "Detail Kategori"
    const val ID_KATEGORI = "id_kategori"
    val routeWithArgs = "$route/{$ID_KATEGORI}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKategoriScreen(
    id: Int,
    onEditClick: (Int) -> Unit = { },
    onViewAllBuku: () -> Unit = { }, // Callback untuk melihat semua buku kategori ini
    onDeleteClick: (Int) -> Unit = { },
    onBackClick: () -> Unit = { },
    modifier: Modifier = Modifier,
    viewModel: DetailKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val kategori = viewModel.uiState.detailUiEvent
    LaunchedEffect(id) {
        viewModel.fetchDetailKategori(id)
    }

    val isLoading = viewModel.uiState.isLoading
    val isError = viewModel.uiState.isError
    val errorMessage = viewModel.uiState.errorMessage

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(DestinasiDetailKategori.titleRes) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(kategori.id_kategori) },
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Kategori")
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                else if (isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else if (viewModel.uiState.isUiEventNotEmpty) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Use Row for each detail with label and value aligned
                                DetailRow(label = "ID Kategori", value = kategori.id_kategori.toString())
                                DetailRow(label = "Nama Kategori", value = kategori.nama_kategori)
                                DetailRow(label = "Deskripsi Kategori", value = kategori.deskripsi_kategori)
                                // New clickable text to view all books related to this category
                                Spacer(modifier = Modifier.height(8.dp))
                                TextButton(onClick = onViewAllBuku) {
                                    Text("Lihat Semua Buku")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = ": $value",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f)
        )
    }
}
