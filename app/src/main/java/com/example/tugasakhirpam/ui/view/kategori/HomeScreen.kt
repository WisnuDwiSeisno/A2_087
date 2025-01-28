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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

object DestinasiHomeKategori : DestinasiNavigasi {
    override val route = "home_kategori"
    override val titleRes = "Home Kategori"
    const val ID_BUKU = "id_buku"
    val routeWithArgs = "$route/{$ID_BUKU}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KategoriHomeScreen(
    id: Int,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = { },
    onKategoriClick: (Int) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeKategori.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.fetchKategoriList() },
                navigateUp = onBackClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kategori")
            }
        },
    ) { innerPadding ->
        KategoriStatus(
            kategoriUiState = viewModel.kategoriUiState.value,
            retryAction = { viewModel.fetchKategoriList() },
            modifier = Modifier.padding(innerPadding),
            onKategoriClick = onKategoriClick,
            onDeleteClick = {viewModel.deleteKategori(it.id_kategori)}
        )
    }
}

@Composable
fun KategoriStatus(
    kategoriUiState: KategoriUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onKategoriClick: (Int) -> Unit,
    onDeleteClick: (Kategori) -> Unit = {},
    ) {
    when (kategoriUiState) {
        is KategoriUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is KategoriUiState.Success -> {
            if (kategoriUiState.kategori.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Kategori")
                }
            } else {
                KategoriList(
                    kategori = kategoriUiState.kategori,
                    modifier = modifier.fillMaxWidth(),
                    onKategoriClick = onKategoriClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is KategoriUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun KategoriList(
    kategori: List<Kategori>,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit = {},
    onKategoriClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kategori) { item ->
            KategoriCard(
                kategori = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onKategoriClick(item.id_kategori)
                    },
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun KategoriCard(
    kategori: Kategori,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = kategori.nama_kategori,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(text = "Deskripsi: ${kategori.deskripsi_kategori}", style = MaterialTheme.typography.bodyMedium)
            IconButton(onClick = { onDeleteClick(kategori) }) {
                Icon(
                    painter = painterResource(R.drawable.delete),
                    contentDescription = "Delete",
                    tint = Color(0xFFE57373)
                )
            }
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}
