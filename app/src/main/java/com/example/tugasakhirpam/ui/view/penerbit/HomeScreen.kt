package com.example.tugasakhirpam.ui.view.penerbit

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
import com.example.tugasakhirpam.data.Penerbit
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
import com.example.tugasakhirpam.viewmodel.penerbit.HomeViewModelPenerbit
import com.example.tugasakhirpam.viewmodel.penerbit.PenerbitUiState

object DestinasiHomePenerbit : DestinasiNavigasi {
    override val route = "home_penerbit"
    override val titleRes = "Home Penerbit"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenerbitHomeScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeViewModelPenerbit = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePenerbit.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getPenerbitList() },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Penerbit")
            }
        },
    ) { innerPadding ->
        PenerbitStatus(
            penerbitUiState = viewModel.penerbitUiState,
            retryAction = { viewModel.getPenerbitList() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { viewModel.deletePenerbit(it.id_penerbit) }
        )
    }
}

@Composable
fun PenerbitStatus(
    penerbitUiState: PenerbitUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penerbit) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (penerbitUiState) {
        is PenerbitUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PenerbitUiState.Success -> {
            if (penerbitUiState.penerbit.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Penerbit")
                }
            } else {
                PenerbitList(
                    penerbit = penerbitUiState.penerbit,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_penerbit) },
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is PenerbitUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PenerbitList(
    penerbit: List<Penerbit>,
    modifier: Modifier = Modifier,
    onDetailClick: (Penerbit) -> Unit,
    onDeleteClick: (Penerbit) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(penerbit) { item ->
            PenerbitCard(
                penerbit = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = { onDeleteClick(item) }
            )
        }
    }
}

@Composable
fun PenerbitCard(
    penerbit: Penerbit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penerbit) -> Unit = {}
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
                    text = penerbit.nama_penerbit,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(penerbit) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
            Text(
                text = "Alamat: ${penerbit.alamat_penerbit}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Telepon: ${penerbit.telepon_penerbit}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
