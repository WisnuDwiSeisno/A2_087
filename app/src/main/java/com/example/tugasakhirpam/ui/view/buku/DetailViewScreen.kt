package com.example.tugasakhirpam.ui.view.buku

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
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.viewmodel.buku.DetailBukuViewModel

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail/{id}"
    override val titleRes = "Detail Buku"
    const val ID_BUKU = "id"
    val routeWithArgs = "$route/{$ID_BUKU}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    id: Int,
    modifier: Modifier = Modifier,
    onEditClick: (Int) -> Unit = { },
    onViewAllKategori: () -> Unit = { }, // New callback for viewing all categories
    onDeleteClick: (Int) -> Unit = { },
    onBackClick: () -> Unit = { },
    viewModel: DetailBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val buku = viewModel.uiState.detailUiEvent
    LaunchedEffect(id) {
        viewModel.fetchDetailBuku(id)
    }

    val isLoading = viewModel.uiState.isLoading
    val isError = viewModel.uiState.isError
    val errorMessage = viewModel.uiState.errorMessage
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeBuku.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.fetchDetailBuku(id) },
                navigateUp = {onBackClick()}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(buku.id_buku) },
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Buku")
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
                                DetailRow(label = "ID Buku", value = buku.id_buku.toString())
                                DetailRow(label = "Nama Buku", value = buku.nama_buku)
                                DetailRow(label = "Deskripsi", value = buku.deskripsi_buku)
                                DetailRow(label = "Tanggal Terbit", value = buku.tanggal_terbit)
                                DetailRow(label = "Status", value = buku.status_buku)
                                DetailRow(label = "ID Kategori", value = buku.id_kategori.toString())
                                DetailRow(label = "ID Penulis", value = buku.id_penulis.toString())
                                DetailRow(label = "ID Penerbit", value = buku.id_penerbit.toString())
                                // New clickable text to view all categories related to this book
                                Spacer(modifier = Modifier.height(8.dp))
                                TextButton(onClick = onViewAllKategori) {
                                    Text("Lihat Semua Kategori")
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
