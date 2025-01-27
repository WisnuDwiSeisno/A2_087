package com.example.tugasakhirpam.ui.view.buku

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
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
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.R
import com.example.tugasakhirpam.data.Buku
import com.example.tugasakhirpam.navigation.DestinasiNavigasi
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.viewmodel.buku.BukuUiState
import com.example.tugasakhirpam.viewmodel.buku.BukuViewModel

object DestinasiHomeBuku : DestinasiNavigasi {
    override val route = "home_buku"
    override val titleRes = "Home Buku"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BukuHomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToPenerbit: () -> Unit,
    navigateToPenulis: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: BukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5DC)), // Warna krem untuk latar belakang
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeBuku.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.fetchBukuList() }
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = navigateToPenulis,
                    shape = RoundedCornerShape(50),
                    containerColor = Color(0xFFB3E5FC) // Warna biru pastel
                ) {
                    Icon(painter = painterResource(R.drawable.writer), contentDescription = "Rute Penulis")
                }

                FloatingActionButton(
                    onClick = navigateToItemEntry,
                    shape = RoundedCornerShape(50),
                    containerColor = Color(0xFFA5D6A7) // Warna hijau pastel
                ) {
                    Icon(painter = painterResource(R.drawable.bookplus), contentDescription = "Add Buku")
                }

                FloatingActionButton(
                    onClick = navigateToPenerbit,
                    shape = RoundedCornerShape(50),
                    containerColor = Color(0xFFB3E5FC) // Warna biru pastel
                ) {
                    Icon(painter = painterResource(R.drawable.penerbit), contentDescription = "Rute Penerbit")
                }
            }
        },
    ) { innerPadding ->
        BukuStatus(
            bukuUiState = viewModel.bukuUiState,
            retryAction = { viewModel.fetchBukuList() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { viewModel.deleteBuku(it.id_buku) }
        )
    }
}

@Composable
fun BukuStatus(
    bukuUiState: BukuUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Buku) -> Unit = {}
) {
    when (bukuUiState) {
        is BukuUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is BukuUiState.Success -> {
            if (bukuUiState.buku.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada data Buku",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.Gray
                    )
                }
            } else {
                BukuList(
                    buku = bukuUiState.buku,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = onDetailClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is BukuUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
        Text(
            text = stringResource(R.string.loading),
            fontSize = 16.sp,
            color = Color(0xFF616161),
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = "Error"
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            color = Color.Red,
            fontFamily = FontFamily.Serif
        )
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373))
        ) {
            Text(stringResource(R.string.retry), color = Color.White)
        }
    }
}

@Composable
fun BukuList(
    buku: List<Buku>,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Buku) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(buku) { item ->
            BukuCard(
                buku = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF0F4C3))
                    .padding(8.dp),
                onDeleteClick = onDeleteClick,
                onDetailClick = onDetailClick
            )
        }
    }
}

@Composable
fun BukuCard(
    buku: Buku,
    modifier: Modifier = Modifier,
    onDeleteClick: (Buku) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onDetailClick(buku.id_buku) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
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
                    text = buku.nama_buku,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF424242),
                    fontFamily = FontFamily.Serif
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(buku) }) {
                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = "Delete",
                        tint = Color(0xFFE57373)
                    )
                }
            }
            Text(
                text = "Deskripsi: ${buku.deskripsi_buku}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF757575)
            )
            Text(
                text = "Tanggal Terbit: ${buku.tanggal_terbit}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF757575)
            )
            Text(
                text = "Status: ${buku.status_buku}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF757575)
            )
        }
    }
}
