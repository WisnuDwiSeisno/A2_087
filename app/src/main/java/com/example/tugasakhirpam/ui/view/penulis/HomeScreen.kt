package com.example.tugasakhirpam.ui.view.penulis

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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.R
import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.data.Kategori
import com.example.tugasakhirpam.data.Penulis
import com.example.tugasakhirpam.navigation.DestinasiNavigasi
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.ui.view.buku.DestinasiDetail
import com.example.tugasakhirpam.ui.view.buku.OnError
import com.example.tugasakhirpam.ui.view.buku.OnLoading
import com.example.tugasakhirpam.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.viewmodel.penulis.HomeViewModelPenulis
import com.example.tugasakhirpam.viewmodel.penulis.PenulisUiState

object DestinasiHomePenulis : DestinasiNavigasi {
    override val route = "home_penulis"
    override val titleRes = "Home Penulis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenulisHomeScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeViewModelPenulis = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePenulis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getPenulisList() },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Penulis")
            }
        },
    ) { innerPadding ->
        PenulisStatus(
            penulisUiState = viewModel.penulisUiState,
            retryAction = { viewModel.getPenulisList() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { viewModel.deletePenulis(it.id_penulis) }
        )
    }
}

@Composable
fun PenulisStatus(
    penulisUiState: PenulisUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penulis) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (penulisUiState) {
        is PenulisUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PenulisUiState.Success -> {
            if (penulisUiState.penulis.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Penulis")
                }
            } else {
                PenulisList(
                    penulis = penulisUiState.penulis,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_penulis) },
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is PenulisUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PenulisList(
    penulis: List<Penulis>,
    modifier: Modifier = Modifier,
    onDetailClick: (Penulis) -> Unit,
    onDeleteClick: (Penulis) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(penulis) { item ->
            PenulisCard(
                penulis = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = { onDeleteClick(item) }
            )
        }
    }
}

@Composable
fun PenulisCard(
    penulis: Penulis,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penulis) -> Unit = {}
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = penulis.nama_penulis,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(penulis) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
            Text(
                text = "Biografi: ${penulis.biografi}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Telepon: ${penulis.kontak}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
